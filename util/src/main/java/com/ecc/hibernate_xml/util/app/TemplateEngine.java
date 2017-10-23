package com.ecc.hibernate_xml.util.app;

import java.io.PrintWriter;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Map;
import java.util.List;

import org.apache.commons.io.FileUtils;

public class TemplateEngine {
	private static final String UPDATE_BUTTON_FORM = "<form action=\"%s\" method=\"GET\"><input type=\"hidden\" name=\"id\" value=\"%s\"><button>Edit</button></form>";
	private static final String DELETE_BUTTON_FORM = "<form action=\"%s\" method=\"POST\"><input type=\"hidden\" name=\"id\" value=\"%s\"><input type=\"hidden\" name=\"mode\" value=\"3\"><button onClick=\"return confirm('Are you sure you want to delete this entry?')\">Delete</button></form>";
	private PrintWriter printWriter;

	public TemplateEngine(PrintWriter printWriter) {
		this.printWriter = printWriter;
	}

	public void render(String templatePath, Map<String, Object> parameters) throws IOException {
		File template = new File(templatePath);
		String buffer = FileUtils.readFileToString(template, Charset.forName("UTF-8"));
		for (Map.Entry<String, Object> parameter: parameters.entrySet()) {
			if (parameter.getValue() != null) {
				buffer = buffer.replaceAll(parameter.getKey(), parameter.getValue().toString());			
			}
		}
		buffer = buffer.replaceAll(":\\w+", "");
		printWriter.println(buffer);
	}

	public String renderTable(List<String> headers, List<List<String>> data, String editAction, String deleteAction) {
		if (data.size() == 0) {
			return "<h6>No records found!</h6>";
		}

		StringBuilder builder = new StringBuilder("<table><thead>");
		for (String header: headers) {
			builder.append(String.format("<th>%s</th>", header));
		}
		builder.append("<th></th><th></th>");
		builder.append("</thead><tbody>");

		for (List<String> row: data) {
			builder.append("<tr>");
			for (String column: row) {
				builder.append(String.format("<td>%s</td>", column));
			}
			String updateButton = String.format(UPDATE_BUTTON_FORM, editAction, row.get(0));
			String deleteButton = String.format(DELETE_BUTTON_FORM, deleteAction, row.get(0));
			builder.append(String.format("<td>%s</td><td>%s</td></tr>", updateButton, deleteButton));
		}
		builder.append("</tbody></table>");

		return builder.toString();
	}
}
