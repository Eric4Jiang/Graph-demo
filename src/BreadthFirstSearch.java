import javax.lang.model.type.NullType;
import javax.swing.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * Created by eric on 10/15/17.
 */
public class BreadthFirstSearch extends SwingWorker <Boolean, NullType>{
    public Graph graph;
    public Queue<Node> Q;

    public BreadthFirstSearch(Graph graph) {
        this.graph = graph;
        this.Q = new LinkedList<>();
    }

    @Override
    protected Boolean doInBackground() throws Exception {

        return true;
    }

    /**
     * Called every time publish() is called.
     * Can safely update GUI here.
     * Just refreshes the graph. The edge color is set beforehand
     *
     * @param chunks - Edge that is being processed
     */
    @Override
    protected void process(List<NullType> chunks) {
        graph.refreshGraph();
    }

    @Override
    protected void done() {

    }
}
