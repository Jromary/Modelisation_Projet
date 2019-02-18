package modele.graph;

import java.util.ArrayList;

public class GraphImplicit extends Graph{
	private int h;
	private int w;
	int[][] interest;
	int N;

	@SuppressWarnings("unchecked")
	public GraphImplicit(int N){
		this.N = N;
	}

	public GraphImplicit(int[][] interest, int w, int h){
		this(w*h + 2);
		this.w = w;
		this.h = h;
		this.interest = interest;
	}

	public int vertices(){
		return N;
	}

	@Override
	public Iterable<Edge> next(int v) {
		ArrayList<Edge> edges = new ArrayList();
		//on est au dernier sommet (t)
		if (v == N - 1){
			return edges;
		}
		//on est au premier sommet (s)
		if (v == N - 2){
			for (int i = 0; i < w; i++) {
				edges.add(new Edge(N-2, i, 0));
			}
			return edges;
		}

		int posX;
		int posY;

		posX = v%w;
		posY = v/w;

		if (posY == h-1){
			edges.add(new Edge(v, N-1, interest[posY][posX]));
			return edges;
		}

		//on max est min pour pas depasser a droite ou a gauche
		edges.add(new Edge(v,(posY+1)*w + Integer.max(0,posX-1), interest[posY][posX]));
		edges.add(new Edge(v,(posY+1)*w + Integer.max(0,posX), interest[posY][posX]));
		edges.add(new Edge(v,(posY+1)*w + Integer.min(w-1,posX+1), interest[posY][posX]));

		return edges;
	}

	@Override
	public Iterable<Edge> prev(int v) {
		ArrayList<Edge> edges = new ArrayList();

		if (v == N-1){
			for (int i = 0; i < w; i++) {
				edges.add(new Edge((h-1)*w + i,N-1, interest[(h-1)][i]));
			}
			return edges;
		}

		if (v == N-2){
			return edges;
		}

		int posX;
		int posY;

		posX = v%w;
		posY = v/w;

		if (posY == 0){
			edges.add(new Edge(N-2, v, 0));
			return edges;
		}

		edges.add(new Edge((posY-1)*w + Integer.max(0, posX -1), v, interest[posY-1][Integer.max(0, posX -1)]));
		edges.add(new Edge((posY-1)*w + posX, v, interest[posY-1][posX]));
		edges.add(new Edge((posY-1)*w + Integer.max(w-1, posX+1), v, interest[posY-1][Integer.min(w-1, posX+1)]));




		return edges;
	}
}
