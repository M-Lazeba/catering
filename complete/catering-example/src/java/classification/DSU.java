package classification;
public class DSU {
	private int p[];
	private int rank[];

	public DSU(int n) {
		p = new int[n];
		rank = new int[n];
		for (int i = 0; i < n; i++)
			p[i] = i;
	}

	public int get(int v) {
		if (p[v] != v)
			p[v] = get(p[v]);
		return p[v];
	}

	public void union(int v, int u) {
		v = get(v);
		u = get(u);
		if (rank[v] <= rank[u])
			p[v] = u;
		else
			p[u] = v;
		if (rank[v] == rank[u])
			rank[u]++;
	}

}
