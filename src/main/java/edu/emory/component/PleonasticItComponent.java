package main.java.edu.emory.component;

import edu.emory.clir.clearnlp.classification.vector.StringFeatureVector;
import edu.emory.clir.clearnlp.component.utils.CFlag;
import edu.emory.clir.clearnlp.dependency.DEPNode;
import edu.emory.clir.clearnlp.dependency.DEPTree;
import edu.emory.clir.clearnlp.reader.TSVReader;
import edu.emory.clir.clearnlp.util.IOUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author alexlutz ({@code ajlutz@emory.edu})
 * @version 1.0
 * @since 9/21/15 at 11:37 PM
 */
public class PleonasticItComponent implements FeatureList
{
    CFlag flag;

    public PleonasticItComponent(CFlag f) {
        flag = f;
    }

    protected StringFeatureVector extractFeatures(DEPTree tree, int id)
    {
        DEPNode node;
        StringFeatureVector vector = new StringFeatureVector();

        //prev x4
        if ((node = tree.get(id-4)) != null) {
            vector.addFeature(PrevPrevPrevPrevPOSTag, node.getPOSTag());
            vector.addFeature(PrevPrevPrevPrevLemma, node.getLemma());
        }

        //prev x3 features
        if ((node = tree.get(id-3)) != null) {
            vector.addFeature(PrevPrevPrevPOSTag, node.getPOSTag());
            vector.addFeature(PrevPrevPrevLemma, node.getLemma());
        }

        //prev x2 features
        if ((node = tree.get(id-2)) != null) {
            vector.addFeature(PrevPrevPOSTag, node.getPOSTag());
            vector.addFeature(PrevPrevLemma, node.getLemma());
        }

        //prev features
        if ((node = tree.get(id-1)) != null) {
            vector.addFeature(PrevPOSTag, node.getPOSTag());
            vector.addFeature(PrevLemma, node.getLemma());
        }

        //current label
        vector.addFeature(CurrentLabel, tree.get(id).getLabel());

        //next features
        if ((node = tree.get(id+1)) != null) {
            vector.addFeature(NextPOSTag, node.getPOSTag());
            vector.addFeature(NextLemma, node.getLemma());
        }

        //next x2 features
        if ((node = tree.get(id+2)) != null) {
            vector.addFeature(NextNextPOSTag, node.getPOSTag());
            vector.addFeature(NextNextLemma, node.getLemma());
        }

        //next x3 features
        if ((node = tree.get(id+3)) != null) {
            vector.addFeature(NextNextNextPOSTag, node.getPOSTag());
            vector.addFeature(NextNextNextLemma, node.getLemma());
        }

        //next x4 features
        if ((node = tree.get(id+4)) != null) {
            vector.addFeature(NextNextNextNextPOSTag, node.getPOSTag());
            vector.addFeature(NextNextNextNextLemma, node.getLemma());
        }

        return vector;
    }

    public void setFlag(CFlag f) {
        flag = f;
    }

    public void process(String path) {
        String label;
        List<DEPTree> trees = extractTrees(path);
        for (DEPTree tree : trees) {
            for (DEPNode node : tree.toNodeArray()) {
                if (node.isLemma("it") && (label = node.getFeat("it")) != null) {
                    if (flag == CFlag.TRAIN) {
                        //want to add new instance here of gold label, feature vector
                    } else if (flag == CFlag.DECODE) {
                        
                    }
                }
            }
        }
    }

    public List<DEPTree> extractTrees(String path) {
        TSVReader reader = new TSVReader(0,1,2,3,4,5,6);
        List<DEPTree> trees = new ArrayList<>();
        DEPTree tree;
        reader.open(IOUtils.createFileInputStream(path));
        while ((tree = reader.next()) != null) {
            trees.add(tree);
        }
        return trees;
    }
}
