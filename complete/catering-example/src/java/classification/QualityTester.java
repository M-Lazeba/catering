package classification;

import java.io.IOException;

public class QualityTester {
	public static void main(String args[]) throws ClassNotFoundException,
			IOException {
		SuperClassificator classificator = new SuperClassificator();
		System.out.println(classificator.classify("����� � ����� � ������ ���� �� ���",
				"�������� ������ ����� ��� ����� ����� ����� ���� ����� "));
	}
}
