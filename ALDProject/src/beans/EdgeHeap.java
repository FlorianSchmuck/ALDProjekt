package beans;

public class EdgeHeap {
	
	private int capicity;
	private WeightedEdge[] pedge;
	private int count = 0;
	private int next = 1;
	
	public int getCount() {
		return count;
	}
	
	public EdgeHeap(int size) {
		this.capicity = size+1;
		pedge = new WeightedEdge[size+1];
	}
	
	private int parent(int pos) {
		return pos / 2; // + Abrunden, weil int-Division 
	}
	
	private int left(int pos) {
		return pos * 2;
	}
	
	private int right(int pos) {
		return (pos * 2) + 1;
	}
	
	private boolean exists(int pos) {
		return (pos <= count && pos > 0);
	}
	
	private int prio(int pos) {
		return pedge[pos].weight;
	}
	
	private void exchange(int pos1, int pos2) {
		WeightedEdge temp;
		temp = pedge[pos1];
		pedge[pos1] = pedge[pos2];
		pedge[pos2] = temp;
	}
	
	private void swim(int pos) {
		int cur, p;
		cur = pos;
		while (exists(cur)) {
			p = parent(cur);
			if (exists(p) && prio(p) > prio(cur)) {
				exchange(cur, p);
				cur = p;
			}
			else {
				break;
			}
		}
	}
	
	private void sink(int pos) {
		int cur, min;
		cur = pos;
		while (exists(cur)) {
			if (!hasChilds(cur)) break;
			min = minChild(cur);
			if (prio(min) < prio(cur)) {
				exchange(min, cur);
				cur = min;
			}
			else {
				break;
			}
		}
	}

	private int minChild(int pos) {
		int min, l, r;
		l = left(pos);
		r = right(pos);
		
		min = l;
		if (exists(r) && prio(r) < prio(l)) {
			min = r;
		}
		return min;
	}
	
	private boolean hasChilds(int pos) {
		int l;
		l = left(pos);
		
		if (exists(l)) return true;
		return false;
	}
	
	public boolean isFull() {
		return (next >= capicity);
	}
	
	public boolean isEmpty() {
		return (count == 0);
	}

	public boolean insert(WeightedEdge pv) {
		if (isFull())
			return false;
		int i = next;
		pedge[i] = pv;
		next++;
		count++;
		swim(i);
		return true;
	}
	
	public WeightedEdge remove() {
		WeightedEdge result;
		if (count == 0)
			return null;
		result = pedge[1];
		pedge[1] = pedge[next-1];
		pedge[next-1] = null;
		next--;
		count--;
		sink(1);
		return result;
	}
	
	public void setPriority(int vertex, int value) {
		for (int i=1; i <= count; i++) {
			if (pedge[i].vertexID == vertex) {
				int oldprio = pedge[i].weight;
				pedge[i].weight = value;
				if (value < oldprio)
					swim(i);
				else
					sink(i);
				break;
			}
		}
	}
	
	public boolean contains(WeightedEdge we) {
		for (int i=1; i <= count; i++) {
			if (pedge[i].vertexID == we.vertexID)
				return true;
		}
		return false;
	}
}