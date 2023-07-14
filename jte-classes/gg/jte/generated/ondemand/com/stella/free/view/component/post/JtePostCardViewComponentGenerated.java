package gg.jte.generated.ondemand.com.stella.free.view.component.post;
import com.stella.free.entity.Post;
import java.util.*;
public final class JtePostCardViewComponentGenerated {
	public static final String JTE_NAME = "com/stella/free/view/component/post/PostCardViewComponent.jte";
	public static final int[] JTE_LINE_INFO = {0,0,1,3,3,3,3,3,3,3,3,3,3,14,14,14,14,14,14,14,14,14,21,21,21,22,22,22,24};
	private static final gg.jte.runtime.BinaryContent BINARY_CONTENT = gg.jte.runtime.BinaryContent.load(JtePostCardViewComponentGenerated.class, "JtePostCardViewComponentGenerated.bin", 242,6,1,182,17,22);
	private static final byte[] TEXT_PART_BINARY_0 = BINARY_CONTENT.get(0);
	private static final byte[] TEXT_PART_BINARY_1 = BINARY_CONTENT.get(1);
	private static final byte[] TEXT_PART_BINARY_2 = BINARY_CONTENT.get(2);
	private static final byte[] TEXT_PART_BINARY_3 = BINARY_CONTENT.get(3);
	private static final byte[] TEXT_PART_BINARY_4 = BINARY_CONTENT.get(4);
	private static final byte[] TEXT_PART_BINARY_5 = BINARY_CONTENT.get(5);
	public static void render(gg.jte.html.HtmlTemplateOutput jteOutput, gg.jte.html.HtmlInterceptor jteHtmlInterceptor, Post post, int pageNum) {
		jteOutput.writeBinaryContent(TEXT_PART_BINARY_0);
		if (gg.jte.runtime.TemplateUtils.isAttributeRendered(post.getThumbnail())) {
			jteOutput.writeBinaryContent(TEXT_PART_BINARY_1);
			jteOutput.setContext("img", "src");
			jteOutput.writeUserContent(post.getThumbnail());
				jteOutput.setContext("img", null);
			jteOutput.writeBinaryContent(TEXT_PART_BINARY_2);
		}
		jteOutput.writeBinaryContent(TEXT_PART_BINARY_3);
		jteOutput.setContext("h2", null);
		jteOutput.writeUserContent(post.getTitle());
		jteOutput.writeBinaryContent(TEXT_PART_BINARY_4);
		jteOutput.setContext("p", null);
		jteOutput.writeUserContent(post.getContent());
		jteOutput.writeBinaryContent(TEXT_PART_BINARY_5);
	}
	public static void renderMap(gg.jte.html.HtmlTemplateOutput jteOutput, gg.jte.html.HtmlInterceptor jteHtmlInterceptor, java.util.Map<String, Object> params) {
		Post post = (Post)params.get("post");
		int pageNum = (int)params.getOrDefault("pageNum", 1);
		render(jteOutput, jteHtmlInterceptor, post, pageNum);
	}
}
