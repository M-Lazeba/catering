package classification;
import java.math.BigDecimal;
import java.math.BigInteger;

public class BernoulliScheme {

	BigInteger c[][];

	public BernoulliScheme(int maxN) {
		c = new BigInteger[maxN + 1][maxN + 1];
		c[0][0] = BigInteger.ONE;
		for (int k = 1; k <= maxN; k++)
			c[0][k] = BigInteger.ZERO;
		for (int n = 1; n <= maxN; n++) {
			for (int k = 0; k <= n; k++) {
				if (k == 0)
					c[n][0] = BigInteger.ONE;
				else
					c[n][k] = c[n - 1][k - 1].add(c[n - 1][k]);
			}
			for (int k = n + 1; k <= maxN; k++)
				c[n][k] = BigInteger.ZERO;
		}
		for (int n = 1; n <= maxN; n++)
			for (int k = n - 1; k >= 0; k--)
				c[n][k] = c[n][k].add(c[n][k + 1]);
	}

	public BigInteger getC(int n, int k) {
		return c[n][k];
	}

	public double getProbability(int n, int k, double p) { // НЕ МЕНЕЕ k успехов
		BigDecimal c = new BigDecimal(getC(n, k));
		c = c.multiply(BigDecimal.valueOf(p).pow(k)
				.multiply(BigDecimal.valueOf(1 - p).pow(n - k)));
		return c.doubleValue();
	}
}
