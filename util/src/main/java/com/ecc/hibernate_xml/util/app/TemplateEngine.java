package com.ecc.hibernate_xml.util;

import java.io.PrintWriter;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Map;

import org.apache.commons.io.FileUtils;

public class TemplateEngine {
	private PrintWriter printWriter;

	public TemplateEngine(PrintWriter printWriter) {
		this.printWriter = printWriter;
	}

	public void render(String templatePath, Map<String, Object> parameters) throws IOException {
		File template = new File(templatePath);
		String buffer = FileUtils.readFileToString(template, Charset.forName("UTF-8"));
		for (Map.Entry<String, Object> parameter : parameters.entrySet()) {
			buffer = buffer.replaceAll(parameter.getKey(), parameter.getValue().toString());
		}
		printWriter.println(buffer);
	}

	// Stub
	public String renderTable() {
		return "<table>			<thead>				<th>ID</th>				<th>Role</th>			</thead>			<tbody>				<td>1</td>				<td>Accountant</td>			</tbody>		</table>";
	}
}
