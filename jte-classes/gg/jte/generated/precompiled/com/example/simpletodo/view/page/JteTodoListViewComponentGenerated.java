package gg.jte.generated.precompiled.com.example.simpletodo.view.page;
import de.tschuehly.spring.viewcomponent.jte.ViewContext;
import com.stella.free.view.page.TodoListViewComponent;
public final class JteTodoListViewComponentGenerated {
	public static final String JTE_NAME = "com/example/simpletodo/view/page/TodoListViewComponent.jte";
	public static final int[] JTE_LINE_INFO = {84,84,85,89,89,89,91,91,91,91,92,92,92,94};
	public static void render(gg.jte.html.HtmlTemplateOutput jteOutput, gg.jte.html.HtmlInterceptor jteHtmlInterceptor, String name) {
		jteOutput.writeContent("\nHello ");
		jteOutput.setContext("html", null);
		jteOutput.writeUserContent(name);
		jteOutput.writeContent("!\nThe current timestamp is ");
		jteOutput.setContext("html", null);
		jteOutput.writeUserContent(System.currentTimeMillis());
		jteOutput.writeContent(".\n\n");
	}
	public static void renderMap(gg.jte.html.HtmlTemplateOutput jteOutput, gg.jte.html.HtmlInterceptor jteHtmlInterceptor, java.util.Map<String, Object> params) {
		String name = (String)params.get("name");
		render(jteOutput, jteHtmlInterceptor, name);
	}
}
