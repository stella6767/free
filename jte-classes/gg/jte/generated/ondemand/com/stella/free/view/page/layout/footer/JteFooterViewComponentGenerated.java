package gg.jte.generated.ondemand.com.stella.free.view.page.layout.footer;
public final class JteFooterViewComponentGenerated {
	public static final String JTE_NAME = "com/stella/free/view/page/layout/footer/FooterViewComponent.jte";
	public static final int[] JTE_LINE_INFO = {1,1,1,1,1,1,1,1,1,1,1,10,10,10,10,11,11,11,12,12,12,12,12,12,12,12,35};
	private static final gg.jte.runtime.BinaryContent BINARY_CONTENT = gg.jte.runtime.BinaryContent.load(JteFooterViewComponentGenerated.class, "JteFooterViewComponentGenerated.bin", 109,21,15,7,1,248);
	private static final byte[] TEXT_PART_BINARY_0 = BINARY_CONTENT.get(0);
	private static final byte[] TEXT_PART_BINARY_1 = BINARY_CONTENT.get(1);
	private static final byte[] TEXT_PART_BINARY_2 = BINARY_CONTENT.get(2);
	private static final byte[] TEXT_PART_BINARY_3 = BINARY_CONTENT.get(3);
	private static final byte[] TEXT_PART_BINARY_4 = BINARY_CONTENT.get(4);
	private static final byte[] TEXT_PART_BINARY_5 = BINARY_CONTENT.get(5);
	public static void render(gg.jte.html.HtmlTemplateOutput jteOutput, gg.jte.html.HtmlInterceptor jteHtmlInterceptor, String creator, String creatorEmail, String githubUrl) {
		jteOutput.writeBinaryContent(TEXT_PART_BINARY_0);
		jteOutput.setContext("p", null);
		jteOutput.writeUserContent(creator);
		jteOutput.writeBinaryContent(TEXT_PART_BINARY_1);
		jteOutput.setContext("p", null);
		jteOutput.writeUserContent(creatorEmail);
		jteOutput.writeBinaryContent(TEXT_PART_BINARY_2);
		if (gg.jte.runtime.TemplateUtils.isAttributeRendered(githubUrl)) {
			jteOutput.writeBinaryContent(TEXT_PART_BINARY_3);
			jteOutput.setContext("a", "href");
			jteOutput.writeUserContent(githubUrl);
				jteOutput.setContext("a", null);
			jteOutput.writeBinaryContent(TEXT_PART_BINARY_4);
		}
		jteOutput.writeBinaryContent(TEXT_PART_BINARY_5);
	}
	public static void renderMap(gg.jte.html.HtmlTemplateOutput jteOutput, gg.jte.html.HtmlInterceptor jteHtmlInterceptor, java.util.Map<String, Object> params) {
		String creator = (String)params.get("creator");
		String creatorEmail = (String)params.get("creatorEmail");
		String githubUrl = (String)params.get("githubUrl");
		render(jteOutput, jteHtmlInterceptor, creator, creatorEmail, githubUrl);
	}
}
