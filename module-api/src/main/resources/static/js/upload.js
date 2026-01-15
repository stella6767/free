
function initUploadJS() {
    console.log('htmx 컨텐츠가 교체되었습니다. 이벤트 리스너를 다시 등록합니다.');
    initializeUploadPage();
}



function initializeUploadPage() {

    console.log("업로드 페이지 초기화 스크립트 실행!");

    const dropZone = document.getElementById('drop-zone');
    // 요소가 없으면 실행 중단
    if (!dropZone) return;

    // --- 1. DOM 요소 가져오기 ---
    const fileInput = document.getElementById('file-input');
    const uploadBtn = document.getElementById('upload-btn');
    const cancelBtn = document.getElementById('cancel-btn');
    const closeBtn = document.getElementById('close-btn');
    const clearBtn = document.getElementById('clear-btn');
    const fileListTbody = document.getElementById('file-list-tbody');
    const emptyRow = document.getElementById('empty-row');
    const fileCountEl = document.getElementById('file-count');
    const totalSizeEl = document.getElementById('total-size');
    const checkAllCheckbox = document.getElementById('check-all');
    const uploadProgressView = document.getElementById('upload-progress-view');
    const uploadCompleteView = document.getElementById('upload-complete-view');
    const closeReportBtn = document.getElementById('close-report-btn');
    const uploadCompleteMsg = document.getElementById('upload-complete-message');


    // --- 2. 상태 변수 정의 ---
    let currentPath = document.querySelector('input[name=currentPath]').value;
    let filesToUpload = [];
    let uploadState = {
        totalSize: 0,
        totalUploaded: 0,
        startTime: 0
    };

    // --- 3. 이벤트 리스너 연결 ---
    dropZone.addEventListener('click', (e) => {
        fileInput.click();
    });
    fileInput.addEventListener('change', (e) => {
        console.log("fileInput.change")
        handleFiles(e.target.files)
        fileInput.value = ''; // 같은 파일 다시 선택가능하게
    });

    // 드래그 앤 드롭 이벤트
    dropZone.addEventListener('dragover', (e) => {
        e.preventDefault();
        dropZone.classList.add('border-primary');
    });
    dropZone.addEventListener('dragleave', (e) => {
        e.preventDefault();
        dropZone.classList.remove('border-primary');
    });
    dropZone.addEventListener('drop', (e) => {
        e.preventDefault();
        dropZone.classList.remove('border-primary');
        handleFiles(e.dataTransfer.files);
    });
    // '전체 선택' 체크박스
    checkAllCheckbox.addEventListener('change', (e) => {
        document.querySelectorAll('.checkbox-item').forEach(cb => cb.checked = e.target.checked);
        updateSummary();
    });

    if (closeReportBtn) {
        closeReportBtn.addEventListener('click', () => {
            uploadCompleteView.style.display = 'none';
        });
    }
    // 지우기 버튼
    clearBtn.addEventListener('click', () => {
        // 1. 체크된 모든 체크박스를 찾습니다.
        const checkedCheckboxes =
            document.querySelectorAll('.checkbox-item:checked');
        // 2. 삭제할 파일들의 인덱스를 수집합니다.
        const indicesToDelete = [];
        checkedCheckboxes.forEach(checkbox => {
            // data-index 속성 값을 가져옵니다. (문자열이므로 숫자로 변환)
            const fileIndex =
                parseInt(checkbox.closest('tr').dataset.fileIndex, 10);
            indicesToDelete.push(fileIndex);
        });

        // 3. filesToUpload 배열을 필터링하여 새로운 배열을 만듭니다.
        //    (삭제할 인덱스 목록에 포함되지 않은 파일들만 남깁니다.)
        filesToUpload = filesToUpload.filter((file, index) => {
            return !indicesToDelete.includes(index);
        });
        //화면 다시 랜더링
        updateFileList();
    });

    uploadBtn.addEventListener('click', () => {
        if (filesToUpload.length > 0) {
            console.log('업로드를 시작합니다:', filesToUpload);
            uploadAllFiles();
        }
    });

    async function uploadAllFiles() {
        // 업로드 시작 시 버튼 비활성화
        uploadBtn.disabled = true;
        uploadBtn.innerHTML = `<span class="loading loading-spinner"></span> 업로드 중...`;
        clearBtn.disabled = true;
        checkAllCheckbox.disabled = true;
        uploadCompleteView.style.display = 'none';
        uploadProgressView.style.display = 'block';
        dropZone.style.display = 'none';
        cancelBtn.style.display = 'none';

        document.querySelectorAll('.checkbox-item').forEach((el) => {
            el.disabled = true
        })

        uploadState = {
            totalSize: filesToUpload.reduce((sum, file) => sum + file.size, 0),
            totalUploaded: 0,
            startTime: Date.now()
        };

        const uploadPromises = filesToUpload.map((file, index) => {
            const tr = fileListTbody.querySelector(`[data-file-index='${index}']`);
            return uploadSingleFileV2(file, tr);
        });

        // 모든 Promise 결과 처리
        const results = await Promise.allSettled(uploadPromises);
        // 최종 결과 집계
        let successCount = results.filter(r => r.status === 'fulfilled').length;
        let failCount = results.length - successCount;

        console.log(`성공: ${successCount}, 실패: ${failCount}`)

        // 2. UI 상태 전환: 업로드 중 -> 완료
        uploadCompleteMsg.textContent = `업로드 완료: ${successCount}개 성공, ${failCount}개 실패`
        uploadProgressView.style.display = 'none';
        uploadCompleteView.style.display = 'block';
        closeBtn.style.display = 'block';

        // 3. 완료 리포트 데이터 채우기
        document.getElementById('successful-files-stats').textContent = `${successCount}개 파일, ${formatBytes(filesToUpload.filter((f, i) => results[i].status === 'fulfilled').reduce((s, f) => s + f.size, 0))}`;
        document.getElementById('failed-files-stats').textContent = `${failCount}개 파일, ${formatBytes(filesToUpload.filter((f, i) => results[i].status === 'rejected').reduce((s, f) => s + f.size, 0))}`;

        uploadBtn.style.display = 'none'
    }


    async function uploadSingleFileV2(file, tr) {
        const statusDiv = tr.querySelector('.status-text');
        let targetObject = ""
        if (currentPath.endsWith('/')) {
            targetObject = currentPath.slice(0, -1)
        }

        try {
            // 1. 서버에 업로드 계획 요청
            statusDiv.textContent = '업로드 중..';
            const initiateResponse = await fetch('/cloud/upload/initiate', {
                method: 'POST',
                headers: {'Content-Type': 'application/json'},
                body: JSON.stringify({
                    filename: file.name,
                    fileSize: file.size,
                    contentType: file.type,
                    targetObjectDir: targetObject
                })
            });
            if (!initiateResponse.ok) throw new Error('업로드 초기화 실패');

            const initData = await initiateResponse.json();

            // 2. 서버의 계획에 따라 다른 함수 호출
            if (initData.uploadType === 'SINGLE') {
                await executePresignedUpload(initData.presignedUrl, file, tr);
            } else if (initData.uploadType === 'MULTIPART') {
                await executeMultipartUpload(initData, file, tr);
            } else {
                throw new Error('알 수 없는 업로드 타입입니다.');
            }
        } catch (error) {
            console.error(`[${file.name}] 업로드 실패:`, error);
            statusDiv.textContent = '실패';
            statusDiv.className = 'status-text text-error font-semibold';
            throw error; // Promise.allSettled가 실패를 감지하도록 에러를 다시 던짐
        }
    }


    function executePresignedUpload(presignedUrl, file, tr) {
        // 단일 파일 업로드는 전체 진행률 계산에 직접 기여해야 함
        const progressCallback = (chunkLoaded) => updateOverallProgress(chunkLoaded);
        return uploadChunkWithXHR(presignedUrl, file, file.type, tr, progressCallback);
    }


    /**
     * XMLHttpRequest를 사용해 실제 데이터(청크)를 업로드하고 진행률을 보고하는 함수
     */
    function uploadChunkWithXHR(url, chunk, contentType, tr, progressCallback) {
        return new Promise((resolve, reject) => {
            const statusDiv = tr.querySelector('.status-text');
            const xhr = new XMLHttpRequest();

            xhr.open('PUT', url, true);
            xhr.setRequestHeader('Content-Type', contentType);

            let lastLoaded = 0;
            if (progressCallback) {
                xhr.upload.onprogress = (event) => {
                    if (event.lengthComputable) {
                        const chunkLoaded = event.loaded - lastLoaded;
                        lastLoaded = event.loaded;
                        progressCallback(chunkLoaded);
                    }
                };
            }

            xhr.onload = () => {
                if (xhr.status === 200) {
                    const etag = xhr.getResponseHeader('ETag');
                    statusDiv.textContent = '성공';
                    statusDiv.className = 'status-text text-success font-semibold';
                    resolve(etag);
                } else {
                    reject(new Error(`업로드 실패: ${xhr.statusText}`));
                }
            };

            xhr.onerror = () => {
                statusDiv.textContent = '실패';
                statusDiv.className = 'status-text text-error font-semibold';
                reject(new Error('네트워크 오류'));
            }
            xhr.send(chunk);
        });
    }



    /**
     * [큰 파일] 멀티파트 업로드 실행
     */
    async function executeMultipartUpload(initiateResponse, file, tr) {

        const fileKey = initiateResponse.fileKey;
        const uploadId = initiateResponse.uploadId;
        const statusDiv = tr.querySelector('.status-text');
        const CHUNK_SIZE = 10 * 1024 * 1024; // 10MB
        const totalChunks = Math.ceil(file.size / CHUNK_SIZE);
        const uploadedParts = [];

        for (let i = 0; i < totalChunks; i++) {
            const partNumber = i + 1;
            const start = i * CHUNK_SIZE;
            const end = start + CHUNK_SIZE;
            const chunk = file.slice(start, end);

            // 1. 각 조각(part)에 대한 사전 서명된 URL 요청
            const partPresignedRes = await fetch('/cloud/upload/part-url', {
                method: 'POST',
                headers: {'Content-Type': 'application/json'},
                body: JSON.stringify({fileKey: fileKey, uploadId: uploadId, partNumber: partNumber})
            });
            if (!partPresignedRes.ok) throw new Error(`${partNumber}번 조각 URL 요청 실패`);

            const result = await partPresignedRes.json();

            // 3. ETag 수집 (매우 중요)
            const etag =
                await uploadChunkWithXHR(result.preSignedUrl, chunk, 'application/octet-stream', tr); // 개별 진행률은 콜백없이
            uploadedParts.push({partNumber, etag});

            // 멀티파트의 전체 진행률은 각 파트 완료 시 업데이트
            updateOverallProgress(chunk.size);
        }

        // 4. 모든 조각 업로드 후, 완료 신호 전송
        await fetch('/cloud/upload/complete', {
            method: 'POST',
            headers: {'Content-Type': 'application/json'},
            body: JSON.stringify({fileKey: fileKey, uploadId: uploadId, parts: uploadedParts})
        });

        statusDiv.textContent = '완료';
        statusDiv.className = 'status-text text-success font-semibold';
    }


    // 업로드 진행률 업데이트 함수
    function updateOverallProgress(chunkLoaded) {

        console.log('updateOverallProgress 호출됨! chunkLoaded:', chunkLoaded);

        uploadState.totalUploaded += chunkLoaded;
        const now = Date.now();
        const timeDiff = (now - uploadState.startTime) / 1000; // 초 단위
        const speed = uploadState.totalUploaded / timeDiff; // Bytes per second

        const percentComplete = uploadState.totalSize > 0 ? (uploadState.totalUploaded / uploadState.totalSize) * 100 : 0;
        document.getElementById('overall-progress-bar').value = percentComplete;
        document.getElementById('overall-progress-percent').textContent = `${Math.round(percentComplete)}%`;

        const remainingSize = uploadState.totalSize - uploadState.totalUploaded;
        const remainingTime = speed > 0 ? remainingSize / speed : Infinity;

        document.getElementById('speed-stats').textContent = `${formatBytes(speed)}/s`;
        document.getElementById('time-remaining-stats').textContent = isFinite(remainingTime) ? `${Math.round(remainingTime)}초` : '-';
        document.getElementById('files-remaining-stats').textContent = `${filesToUpload.length}개, ${formatBytes(remainingSize)}`;

    }


    // 파일 처리 함수
    function handleFiles(files) {
        for (const file of files) {
            // 중복 파일 체크 (선택사항)
            // if (!filesToUpload.some(f => f.name === file.name && f.size === file.size)) {
            //     filesToUpload.push(file);
            // }
            console.log("file", file)
            filesToUpload.push(file);
        }
        updateFileList();
    }

    // 파일 목록 UI 업데이트 함수
    function updateFileList() {
        fileListTbody.innerHTML = ''; // 목록 비우기
        if (filesToUpload.length === 0) {
            fileListTbody.appendChild(emptyRow);
            uploadBtn.disabled = true;
        } else {
            uploadBtn.disabled = false;
            filesToUpload.forEach((file, index) => {
                const tr = document.createElement('tr');
                // ★★★ tr에 data-index 속성으로 인덱스 저장 ★★★
                tr.dataset.fileIndex = index;
                tr.innerHTML = `
                    <th><input type="checkbox" class="checkbox checkbox-item"></th>
                    <td class="truncate">${file.name}</td>
                    <td>${file.type || '알 수 없음'}</td>
                    <td>${formatBytes(file.size)}</td>
                    <td>
                        <div class="status-text">대기</div>
                    </td>
                `;

                // 새로 생성된 체크박스를 찾아서 'change' 이벤트가 발생하면 상태를 업데이트하도록 합니다.
                tr.querySelector('.checkbox-item').addEventListener('change', updateSummary);

                fileListTbody.appendChild(tr);
            });
        }
        // 파일 개수 및 총 크기 업데이트
        updateSummary();
    }

    function updateSummary() {
        const fileCount = filesToUpload.length;
        fileCountEl.textContent = fileCount;
        const totalSize =
            filesToUpload.reduce((sum, file) => sum + file.size, 0);
        totalSizeEl.textContent = formatBytes(totalSize);

        // ★★★ 버튼 상태 업데이트 로직 ★★★
        const checkedCount =
            document.querySelectorAll('.checkbox-item:checked').length;

        console.log("checkedCount", checkedCount)

        // '선택 항목 삭제' 버튼: 체크된 것이 1개 이상이면 활성화
        clearBtn.disabled = checkedCount === 0;

        // '업로드' 버튼: 업로드할 파일이 1개 이상이면 활성화
        uploadBtn.disabled = fileCount === 0;


        const checkAll = document.getElementById('check-all');
        if (checkAll) {
            checkAll.checked = fileCount > 0 && checkedCount === fileCount;
        }

    }

    // 파일 크기 포맷팅 함수
    function formatBytes(bytes, decimals = 2) {
        if (bytes === 0) return '0 B';
        const k = 1024;
        const dm = decimals < 0 ? 0 : decimals;
        const sizes = ['B', 'KB', 'MB', 'GB', 'TB'];
        const i = Math.floor(Math.log(bytes) / Math.log(k));
        return parseFloat((bytes / Math.pow(k, i)).toFixed(dm)) + ' ' + sizes[i];
    }
}

document.addEventListener('DOMContentLoaded', initializeUploadPage);
