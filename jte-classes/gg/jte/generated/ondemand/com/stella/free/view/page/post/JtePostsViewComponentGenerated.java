package gg.jte.generated.ondemand.com.stella.free.view.page.post;
import com.stella.free.entity.Post;
import com.stella.free.view.component.post.PostCardViewComponent;
import org.springframework.data.domain.Page;
public final class JtePostsViewComponentGenerated {
	public static final String JTE_NAME = "com/stella/free/view/page/post/PostsViewComponent.jte";
	public static final int[] JTE_LINE_INFO = {0,0,1,2,4,4,4,4,4,4,4,4,4,4,4,4,4,4,9,9,9,13,13,13,13,16,16,21,21,23,23,24,24,24,25,25,27,28,30};
	private static final gg.jte.runtime.BinaryContent BINARY_CONTENT = gg.jte.runtime.BinaryContent.load(JtePostsViewComponentGenerated.class, "JtePostsViewComponentGenerated.bin", 3,125,64,114,10,13,9,10,9,12);
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
	public static void render(gg.jte.html.HtmlTemplateOutput jteOutput, gg.jte.html.HtmlInterceptor jteHtmlInterceptor, Page<Post> posts, PostCardViewComponent postCardViewComponent) {
		jteOutput.writeBinaryContent(TEXT_PART_BINARY_0);
		if (!posts.isLast()) {
			jteOutput.writeBinaryContent(TEXT_PART_BINARY_1);
			jteOutput.setContext("div", "hx-get");
			jteOutput.writeUserContent(posts.getPageable().getPageNumber() + 1);
				jteOutput.setContext("div", null);
			jteOutput.writeBinaryContent(TEXT_PART_BINARY_2);
		} else {
			jteOutput.writeBinaryContent(TEXT_PART_BINARY_3);
		}
		jteOutput.writeBinaryContent(TEXT_PART_BINARY_4);
		for (Post post : posts.getContent()) {
			jteOutput.writeBinaryContent(TEXT_PART_BINARY_5);
			jteOutput.setContext("div", null);
			jteOutput.writeUserContent(postCardViewComponent.render(post));
			jteOutput.writeBinaryContent(TEXT_PART_BINARY_6);
		}
		jteOutput.writeBinaryContent(TEXT_PART_BINARY_7);
		jteOutput.writeBinaryContent(TEXT_PART_BINARY_8);
		jteOutput.writeBinaryContent(TEXT_PART_BINARY_9);
	}
	public static void renderMap(gg.jte.html.HtmlTemplateOutput jteOutput, gg.jte.html.HtmlInterceptor jteHtmlInterceptor, java.util.Map<String, Object> params) {
		Page<Post> posts = (Page<Post>)params.get("posts");
		PostCardViewComponent postCardViewComponent = (PostCardViewComponent)params.get("postCardViewComponent");
		render(jteOutput, jteHtmlInterceptor, posts, postCardViewComponent);
	}
}
