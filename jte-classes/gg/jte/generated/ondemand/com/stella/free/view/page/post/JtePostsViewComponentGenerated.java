package gg.jte.generated.ondemand.com.stella.free.view.page.post;
import com.stella.free.entity.Post;
import com.stella.free.view.component.post.PostCardViewComponent;
import org.springframework.data.domain.Page;
public final class JtePostsViewComponentGenerated {
	public static final String JTE_NAME = "com/stella/free/view/page/post/PostsViewComponent.jte";
	public static final int[] JTE_LINE_INFO = {0,0,1,2,4,4,4,4,4,4,4,4,4,4,4,10,10,10,10,10,15,15,16,16,16,17,17,19,20,22};
	private static final gg.jte.runtime.BinaryContent BINARY_CONTENT = gg.jte.runtime.BinaryContent.load(JtePostsViewComponentGenerated.class, "JtePostsViewComponentGenerated.bin", 125,66,9,5,2,1,8);
	private static final byte[] TEXT_PART_BINARY_0 = BINARY_CONTENT.get(0);
	private static final byte[] TEXT_PART_BINARY_1 = BINARY_CONTENT.get(1);
	private static final byte[] TEXT_PART_BINARY_2 = BINARY_CONTENT.get(2);
	private static final byte[] TEXT_PART_BINARY_3 = BINARY_CONTENT.get(3);
	private static final byte[] TEXT_PART_BINARY_4 = BINARY_CONTENT.get(4);
	private static final byte[] TEXT_PART_BINARY_5 = BINARY_CONTENT.get(5);
	private static final byte[] TEXT_PART_BINARY_6 = BINARY_CONTENT.get(6);
	public static void render(gg.jte.html.HtmlTemplateOutput jteOutput, gg.jte.html.HtmlInterceptor jteHtmlInterceptor, Page<Post> posts, PostCardViewComponent postCardViewComponent) {
		jteOutput.writeBinaryContent(TEXT_PART_BINARY_0);
		jteOutput.setContext("div", "hx-get");
		jteOutput.writeUserContent(posts.getPageable().getPageNumber() + 1);
			jteOutput.setContext("div", null);
		jteOutput.writeBinaryContent(TEXT_PART_BINARY_1);
		for (Post post : posts.getContent()) {
			jteOutput.writeBinaryContent(TEXT_PART_BINARY_2);
			jteOutput.setContext("div", null);
			jteOutput.writeUserContent(postCardViewComponent.render(post));
			jteOutput.writeBinaryContent(TEXT_PART_BINARY_3);
		}
		jteOutput.writeBinaryContent(TEXT_PART_BINARY_4);
		jteOutput.writeBinaryContent(TEXT_PART_BINARY_5);
		jteOutput.writeBinaryContent(TEXT_PART_BINARY_6);
	}
	public static void renderMap(gg.jte.html.HtmlTemplateOutput jteOutput, gg.jte.html.HtmlInterceptor jteHtmlInterceptor, java.util.Map<String, Object> params) {
		Page<Post> posts = (Page<Post>)params.get("posts");
		PostCardViewComponent postCardViewComponent = (PostCardViewComponent)params.get("postCardViewComponent");
		render(jteOutput, jteHtmlInterceptor, posts, postCardViewComponent);
	}
}