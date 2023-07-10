package gg.jte.generated.ondemand.com.stella.free.view.component.pagination;
import org.springframework.data.domain.Page;
public final class JtePaginationViewComponentGenerated {
	public static final String JTE_NAME = "com/stella/free/view/component/pagination/PaginationViewComponent.jte";
	public static final int[] JTE_LINE_INFO = {0,0,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,9,9,9,10,10,11,11,13,13,13,13,16,16,18,18,20,20,20,20,20,20,20,20,22,22,25,25,27,27,31,31,31,31,31,31,31,31,32,32,32,32,32,32,32,32,36,36,40,40,40,40,40,40,40,40,41,41,41,41,41,41,41,41,44,44,46,46,48,48,50,50,52,52,52,52,52,52,52,52,55,55,58,58,58,58,58,58,58,58,66};
	private static final gg.jte.runtime.BinaryContent BINARY_CONTENT = gg.jte.runtime.BinaryContent.load(JtePaginationViewComponentGenerated.class, "JtePaginationViewComponentGenerated.bin", 69,9,9,65,35,74,88,6,38,11,14,130,13,1,56,6,66,130,13,1,56,6,35,10,10,74,88,6,51,81,6,48);
	private static final byte[] TEXT_PART_BINARY_0 = BINARY_CONTENT.get(0);
	private static final byte[] TEXT_PART_BINARY_1 = BINARY_CONTENT.get(1);
	private static final byte[] TEXT_PART_BINARY_2 = BINARY_CONTENT.get(2);
	private static final byte[] TEXT_PART_BINARY_3 = BINARY_CONTENT.get(3);
	private static final byte[] TEXT_PART_BINARY_4 = BINARY_CONTENT.get(4);
	private static final byte[] TEXT_PART_BINARY_5 = BINARY_CONTENT.get(5);
	private static final byte[] TEXT_PART_BINARY_6 = BINARY_CONTENT.get(6);
	private static final byte[] TEXT_PART_BINARY_7 = BINARY_CONTENT.get(7);
	private static final byte[] TEXT_PART_BINARY_8 = BINARY_CONTENT.get(8);
	private static final byte[] TEXT_PART_BINARY_9 = BINARY_CONTENT.get(9);
	private static final byte[] TEXT_PART_BINARY_10 = BINARY_CONTENT.get(10);
	private static final byte[] TEXT_PART_BINARY_11 = BINARY_CONTENT.get(11);
	private static final byte[] TEXT_PART_BINARY_12 = BINARY_CONTENT.get(12);
	private static final byte[] TEXT_PART_BINARY_13 = BINARY_CONTENT.get(13);
	private static final byte[] TEXT_PART_BINARY_14 = BINARY_CONTENT.get(14);
	private static final byte[] TEXT_PART_BINARY_15 = BINARY_CONTENT.get(15);
	private static final byte[] TEXT_PART_BINARY_16 = BINARY_CONTENT.get(16);
	private static final byte[] TEXT_PART_BINARY_17 = BINARY_CONTENT.get(17);
	private static final byte[] TEXT_PART_BINARY_18 = BINARY_CONTENT.get(18);
	private static final byte[] TEXT_PART_BINARY_19 = BINARY_CONTENT.get(19);
	private static final byte[] TEXT_PART_BINARY_20 = BINARY_CONTENT.get(20);
	private static final byte[] TEXT_PART_BINARY_21 = BINARY_CONTENT.get(21);
	private static final byte[] TEXT_PART_BINARY_22 = BINARY_CONTENT.get(22);
	private static final byte[] TEXT_PART_BINARY_23 = BINARY_CONTENT.get(23);
	private static final byte[] TEXT_PART_BINARY_24 = BINARY_CONTENT.get(24);
	private static final byte[] TEXT_PART_BINARY_25 = BINARY_CONTENT.get(25);
	private static final byte[] TEXT_PART_BINARY_26 = BINARY_CONTENT.get(26);
	private static final byte[] TEXT_PART_BINARY_27 = BINARY_CONTENT.get(27);
	private static final byte[] TEXT_PART_BINARY_28 = BINARY_CONTENT.get(28);
	private static final byte[] TEXT_PART_BINARY_29 = BINARY_CONTENT.get(29);
	private static final byte[] TEXT_PART_BINARY_30 = BINARY_CONTENT.get(30);
	private static final byte[] TEXT_PART_BINARY_31 = BINARY_CONTENT.get(31);
	public static void render(gg.jte.html.HtmlTemplateOutput jteOutput, gg.jte.html.HtmlInterceptor jteHtmlInterceptor, Page<Object> pages, String endpoint) {
		jteOutput.writeBinaryContent(TEXT_PART_BINARY_0);
		var maxPage = 5;
		jteOutput.writeBinaryContent(TEXT_PART_BINARY_1);
		var start =  Math.floor (pages.getNumber() / maxPage) * maxPage + 1;
		jteOutput.writeBinaryContent(TEXT_PART_BINARY_2);
		var end = (start + maxPage -1 ) < pages.getTotalPages() ? start + maxPage -1 : pages.getTotalPages(); 
		jteOutput.writeBinaryContent(TEXT_PART_BINARY_3);
		jteOutput.setContext("button", "onclick");
		jteOutput.writeUserContent(endpoint);
			jteOutput.setContext("button", null);
		jteOutput.writeBinaryContent(TEXT_PART_BINARY_4);
		if (pages.isFirst() ) {
			jteOutput.writeBinaryContent(TEXT_PART_BINARY_5);
		} else {
			jteOutput.writeBinaryContent(TEXT_PART_BINARY_6);
			jteOutput.setContext("button", "onclick");
			jteOutput.writeUserContent(endpoint);
				jteOutput.setContext("button", null);
			jteOutput.writeBinaryContent(TEXT_PART_BINARY_7);
			jteOutput.setContext("button", "onclick");
			jteOutput.writeUserContent(pages.getNumber()-1);
				jteOutput.setContext("button", null);
			jteOutput.writeBinaryContent(TEXT_PART_BINARY_8);
		}
		jteOutput.writeBinaryContent(TEXT_PART_BINARY_9);
		for (int i = (int) start; i <= end;  i++ ) {
			jteOutput.writeBinaryContent(TEXT_PART_BINARY_10);
			if ( i == (pages.getNumber() + 1)) {
				jteOutput.writeBinaryContent(TEXT_PART_BINARY_11);
				if (gg.jte.runtime.TemplateUtils.isAttributeRendered(i)) {
					jteOutput.writeBinaryContent(TEXT_PART_BINARY_12);
					jteOutput.setContext("input", "aria-label");
					jteOutput.writeUserContent(i);
						jteOutput.setContext("input", null);
					jteOutput.writeBinaryContent(TEXT_PART_BINARY_13);
				}
				jteOutput.writeBinaryContent(TEXT_PART_BINARY_14);
				jteOutput.setContext("input", "onclick");
				jteOutput.writeUserContent(endpoint);
					jteOutput.setContext("input", null);
				jteOutput.writeBinaryContent(TEXT_PART_BINARY_15);
				jteOutput.setContext("input", "onclick");
				jteOutput.writeUserContent(i -1);
					jteOutput.setContext("input", null);
				jteOutput.writeBinaryContent(TEXT_PART_BINARY_16);
			} else {
				jteOutput.writeBinaryContent(TEXT_PART_BINARY_17);
				if (gg.jte.runtime.TemplateUtils.isAttributeRendered(i)) {
					jteOutput.writeBinaryContent(TEXT_PART_BINARY_18);
					jteOutput.setContext("input", "aria-label");
					jteOutput.writeUserContent(i);
						jteOutput.setContext("input", null);
					jteOutput.writeBinaryContent(TEXT_PART_BINARY_19);
				}
				jteOutput.writeBinaryContent(TEXT_PART_BINARY_20);
				jteOutput.setContext("input", "onclick");
				jteOutput.writeUserContent(endpoint);
					jteOutput.setContext("input", null);
				jteOutput.writeBinaryContent(TEXT_PART_BINARY_21);
				jteOutput.setContext("input", "onclick");
				jteOutput.writeUserContent(i - 1);
					jteOutput.setContext("input", null);
				jteOutput.writeBinaryContent(TEXT_PART_BINARY_22);
			}
			jteOutput.writeBinaryContent(TEXT_PART_BINARY_23);
		}
		jteOutput.writeBinaryContent(TEXT_PART_BINARY_24);
		if (pages.isLast() ) {
			jteOutput.writeBinaryContent(TEXT_PART_BINARY_25);
		} else {
			jteOutput.writeBinaryContent(TEXT_PART_BINARY_26);
			jteOutput.setContext("button", "onclick");
			jteOutput.writeUserContent(endpoint);
				jteOutput.setContext("button", null);
			jteOutput.writeBinaryContent(TEXT_PART_BINARY_27);
			jteOutput.setContext("button", "onclick");
			jteOutput.writeUserContent(pages.getNumber()+1);
				jteOutput.setContext("button", null);
			jteOutput.writeBinaryContent(TEXT_PART_BINARY_28);
		}
		jteOutput.writeBinaryContent(TEXT_PART_BINARY_29);
		jteOutput.setContext("button", "onclick");
		jteOutput.writeUserContent(endpoint);
			jteOutput.setContext("button", null);
		jteOutput.writeBinaryContent(TEXT_PART_BINARY_30);
		jteOutput.setContext("button", "onclick");
		jteOutput.writeUserContent(pages.getTotalPages() -1);
			jteOutput.setContext("button", null);
		jteOutput.writeBinaryContent(TEXT_PART_BINARY_31);
	}
	public static void renderMap(gg.jte.html.HtmlTemplateOutput jteOutput, gg.jte.html.HtmlInterceptor jteHtmlInterceptor, java.util.Map<String, Object> params) {
		Page<Object> pages = (Page<Object>)params.get("pages");
		String endpoint = (String)params.get("endpoint");
		render(jteOutput, jteHtmlInterceptor, pages, endpoint);
	}
}
