package gg.jte.generated.ondemand.com.example.simpletodo.view.component;
import com.example.simpletodo.entity.Todo;
import java.util.*;
public final class JteTodoViewComponentGenerated {
	public static final String JTE_NAME = "com/example/simpletodo/view/component/TodoViewComponent.jte";
	public static final int[] JTE_LINE_INFO = {0,0,1,3,3,3,3,3,3,3,3,3,3,3,3,7,7,7,7,7,11,11,11,11,11,11,11,14,14,14,14,15,15,15,15,18,18,18,24,24,24,24,31};
	private static final gg.jte.runtime.BinaryContent BINARY_CONTENT = gg.jte.runtime.BinaryContent.load(JteTodoViewComponentGenerated.class, "JteTodoViewComponentGenerated.bin", 27,103,2,196,45,60,289,107);
	private static final byte[] TEXT_PART_BINARY_0 = BINARY_CONTENT.get(0);
	private static final byte[] TEXT_PART_BINARY_1 = BINARY_CONTENT.get(1);
	private static final byte[] TEXT_PART_BINARY_2 = BINARY_CONTENT.get(2);
	private static final byte[] TEXT_PART_BINARY_3 = BINARY_CONTENT.get(3);
	private static final byte[] TEXT_PART_BINARY_4 = BINARY_CONTENT.get(4);
	private static final byte[] TEXT_PART_BINARY_5 = BINARY_CONTENT.get(5);
	private static final byte[] TEXT_PART_BINARY_6 = BINARY_CONTENT.get(6);
	private static final byte[] TEXT_PART_BINARY_7 = BINARY_CONTENT.get(7);
	public static void render(gg.jte.html.HtmlTemplateOutput jteOutput, gg.jte.html.HtmlInterceptor jteHtmlInterceptor, Todo todo) {
		jteOutput.writeBinaryContent(TEXT_PART_BINARY_0);
		jteOutput.setContext("div", "id");
		jteOutput.writeUserContent(todo.getId());
			jteOutput.setContext("div", null);
		jteOutput.writeBinaryContent(TEXT_PART_BINARY_1);
		jteOutput.setContext("p", "class");
		jteOutput.writeUserContent(todo.getStatus() == true ? "line-through" : "" );
			jteOutput.setContext("p", null);
		jteOutput.writeBinaryContent(TEXT_PART_BINARY_2);
		jteOutput.setContext("p", null);
		jteOutput.writeUserContent(todo.getContent());
		jteOutput.writeBinaryContent(TEXT_PART_BINARY_3);
		jteOutput.setContext("button", "hx-put");
		jteOutput.writeUserContent(todo.getId());
			jteOutput.setContext("button", null);
		jteOutput.writeBinaryContent(TEXT_PART_BINARY_4);
		jteOutput.setContext("button", "hx-target");
		jteOutput.writeUserContent(todo.getId());
			jteOutput.setContext("button", null);
		jteOutput.writeBinaryContent(TEXT_PART_BINARY_5);
		jteOutput.setContext("button", null);
		jteOutput.writeUserContent(todo.getStatus() == true ? "Yet" : "Done");
		jteOutput.writeBinaryContent(TEXT_PART_BINARY_6);
		jteOutput.setContext("button", "hx-delete");
		jteOutput.writeUserContent(todo.getId());
			jteOutput.setContext("button", null);
		jteOutput.writeBinaryContent(TEXT_PART_BINARY_7);
	}
	public static void renderMap(gg.jte.html.HtmlTemplateOutput jteOutput, gg.jte.html.HtmlInterceptor jteHtmlInterceptor, java.util.Map<String, Object> params) {
		Todo todo = (Todo)params.get("todo");
		render(jteOutput, jteHtmlInterceptor, todo);
	}
}
