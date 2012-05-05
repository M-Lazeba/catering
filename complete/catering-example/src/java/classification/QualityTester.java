package classification;

import java.io.IOException;

public class QualityTester {
	public static void main(String args[]) throws ClassNotFoundException,
			IOException {
		SuperClassificator classificator = new SuperClassificator();
		System.out.println(classificator.classify("салат с котом и говном выал вы овы",
				"помидоры огурцы салат кот срань члены говно овал выоал "));
	}
}
