import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
 
public class DicisionTree {
 
    public static void main(String[] args) throws Exception {
        String[] attrNames = new String[] { "AGE", "INCOME", "STUDENT",
                "CREDIT_RATING" };
 
        // ��ȡ������
        Map<Object, List<Sample>> samples = readSamples(attrNames);
 
        // ���ɾ�����
        Object decisionTree = generateDecisionTree(samples, attrNames);
 
        // ���������
        outputDecisionTree(decisionTree, 0, null);
    }
 
    /**
     * ��ȡ�ѷ����������������Map������ -> ���ڸ÷�����������б�
     */
    static Map<Object, List<Sample>> readSamples(String[] attrNames) { 
        // �������Լ����������ࣨ�����е����һ��Ԫ��Ϊ�����������ࣩ
        Object[][] rawData = new Object[][] {
                { "<30  ", "High  ", "No ", "Fair     ", "0" },
                { "<30  ", "High  ", "No ", "Excellent", "0" },
                { "30-40", "High  ", "No ", "Fair     ", "1" },
                { ">40  ", "Medium", "No ", "Fair     ", "1" },
                { ">40  ", "Low   ", "Yes", "Fair     ", "1" },
                { ">40  ", "Low   ", "Yes", "Excellent", "0" },
                { "30-40", "Low   ", "Yes", "Excellent", "1" },
                { "<30  ", "Medium", "No ", "Fair     ", "0" },
                { "<30  ", "Low   ", "Yes", "Fair     ", "1" },
                { ">40  ", "Medium", "Yes", "Fair     ", "1" },
                { "<30  ", "Medium", "Yes", "Excellent", "1" },
                { "30-40", "Medium", "No ", "Excellent", "1" },
                { "30-40", "High  ", "Yes", "Fair     ", "1" },
                { ">40  ", "Medium", "No ", "Excellent", "0" } };
 
        // ��ȡ�������Լ����������࣬�����ʾ������Sample���󣬲������໮��������
        Map<Object, List<Sample>> ret = new HashMap<Object, List<Sample>>();
        for (Object[] row : rawData) {   			//����Ϊ�� ÿһ���������еĲ�����ÿ����������һ��Object[]��ʵ��������װ��Ϊrawdata��ÿһ��Ԫ�أ������ϱ��е�ÿһ��
            Sample sample = new Sample();
            int i = 0;
            for (int n = row.length - 1; i < n; i++)
                sample.setAttribute(attrNames[i], row[i]);
            
            sample.setCategory(row[i]);				//��������������һ���Ǵ���������ԣ�����ֱ����row[i]
            List<Sample> samples = ret.get(row[i]); //�õ����Ϊrow[i]��rawsample��
            if (samples == null) {                  //����Ǹ�����ǿյľͳ�ʼ��������
                samples = new LinkedList<Sample>();
                ret.put(row[i], samples);           //���Ұ���������ӵ�ret���hashmap��ȥ����ʱsamples���Ǹ�������������
            }
            samples.add(sample);                    //��samples��������������������ʱ���Ǹ�����
        }											//forѭ��֮�󼴿���������İ����������ԵĻ���
 
        return ret;
    }
 
    /**
     * ���������
     */
    static Object generateDecisionTree(
            Map<Object, List<Sample>> categoryToSamples, String[] attrNames) {
 
        // ���ֻ��һ��������������������������Ϊ�������ķ���
        if (categoryToSamples.size() == 1)
            return categoryToSamples.keySet().iterator().next();
 
        // ���û�й����ߵ����ԣ����������о�����������ķ�����Ϊ�������ķ��࣬��ͶƱѡ�ٳ�����
        if (attrNames.length == 0) {
            int max = 0;
            Object maxCategory = null;
            for (Entry<Object, List<Sample>> entry : categoryToSamples
                    .entrySet()) {
                int cur = entry.getValue().size();
                if (cur > max) {
                    max = cur;
                    maxCategory = entry.getKey();
                }
            }
            return maxCategory;
        }
 
        // ѡȡ��������
        Object[] rst = chooseBestTestAttribute(categoryToSamples, attrNames);
 
        // ����������㣬��֧����Ϊѡȡ�Ĳ�������
        Tree tree = new Tree(attrNames[(Integer) rst[0]]);
 
        // ���ù��Ĳ������Բ�Ӧ�ٴα�ѡΪ��������
        String[] subA = new String[attrNames.length - 1];
        for (int i = 0, j = 0; i < attrNames.length; i++)
            if (i != (Integer) rst[0])
                subA[j++] = attrNames[i];
 
        // ���ݷ�֧�������ɷ�֧
        @SuppressWarnings("unchecked")
        Map<Object, Map<Object, List<Sample>>> splits =
        /* NEW LINE */(Map<Object, Map<Object, List<Sample>>>) rst[2];
        for (Entry<Object, Map<Object, List<Sample>>> entry : splits.entrySet()) {
            Object attrValue = entry.getKey();
            Map<Object, List<Sample>> split = entry.getValue();
            Object child = generateDecisionTree(split, subA);
            tree.setChild(attrValue, child);
        }
 
        return tree;
    }
 
    /**
     * ѡȡ���Ų������ԡ�������ָ�������ѡȡ�Ĳ������Է�֧����Ӹ���֧ȷ��������
     * �ķ�����Ҫ����Ϣ��֮����С����ȼ���ȷ���������Ĳ������Ի�õ���Ϣ�������
     * �������飺ѡȡ�������±ꡢ��Ϣ��֮�͡�Map(����ֵ->(����->�����б�))
     */
    static Object[] chooseBestTestAttribute(
            Map<Object, List<Sample>> categoryToSamples, String[] attrNames) {
 
        int minIndex = -1; // ���������±�
        double minValue = Double.MAX_VALUE; // ��С��Ϣ��
        Map<Object, Map<Object, List<Sample>>> minSplits = null; // ���ŷ�֧����
 
        // ��ÿһ�����ԣ����㽫����Ϊ�������Ե�������ڸ���֧ȷ���������ķ�����Ҫ����Ϣ��֮�ͣ�ѡȡ��СΪ����
        for (int attrIndex = 0; attrIndex < attrNames.length; attrIndex++) {
            int allCount = 0; // ͳ�����������ļ�����
 
            // ����ǰ���Թ���Map������ֵ->(����->�����б�)
            Map<Object, Map<Object, List<Sample>>> curSplits =
            /* NEW LINE */new HashMap<Object, Map<Object, List<Sample>>>();
            for (Entry<Object, List<Sample>> entry : categoryToSamples
                    .entrySet()) {
                Object category = entry.getKey();
                List<Sample> samples = entry.getValue();
                for (Sample sample : samples) {
                    Object attrValue = sample.getAttribute(attrNames[attrIndex]);
                    Map<Object, List<Sample>> split = curSplits.get(attrValue);
                    if (split == null) {
                        split = new HashMap<Object, List<Sample>>();
                        curSplits.put(attrValue, split);
                    }
                    List<Sample> splitSamples = split.get(category);
                    if (splitSamples == null) {
                        splitSamples = new LinkedList<Sample>();
                        split.put(category, splitSamples);
                    }
                    splitSamples.add(sample);
                }
                allCount += samples.size();
            }
 
            // ���㽫��ǰ������Ϊ�������Ե�������ڸ���֧ȷ���������ķ�����Ҫ����Ϣ��֮��
            double curValue = 0.0; // ���������ۼӸ���֧
            for (Map<Object, List<Sample>> splits : curSplits.values()) {
                double perSplitCount = 0;
                for (List<Sample> list : splits.values())
                    perSplitCount += list.size(); // �ۼƵ�ǰ��֧������
                double perSplitValue = 0.0; // ����������ǰ��֧
                for (List<Sample> list : splits.values()) {
                    double p = list.size() / perSplitCount;
                    perSplitValue -= p * (Math.log(p) / Math.log(2));
                }
                curValue += (perSplitCount / allCount) * perSplitValue;
            }
 
            // ѡȡ��СΪ����
            if (minValue > curValue) {
                minIndex = attrIndex;
                minValue = curValue;
                minSplits = curSplits;
            }
        }
 
        return new Object[] { minIndex, minValue, minSplits };
    }
 
    /**
     * ���������������׼���
     */
    static void outputDecisionTree(Object obj, int level, Object from) {
        for (int i = 0; i < level; i++)
            System.out.print("|-----");
        if (from != null)
            System.out.printf("(%s):", from);
        if (obj instanceof Tree) {
            Tree tree = (Tree) obj;
            String attrName = tree.getAttribute();
            System.out.printf("[%s = ?]\n", attrName);
            for (Object attrValue : tree.getAttributeValues()) {
                Object child = tree.getChild(attrValue);
                outputDecisionTree(child, level + 1, attrName + " = "
                        + attrValue);
            }
        } else {
            System.out.printf("[CATEGORY = %s]\n", obj);
        }
    }
 
    /**
     * ����������������Ժ�һ��ָ��������������ķ���ֵ
     */
    static class Sample {
 
        private Map<String, Object> attributes = new HashMap<String, Object>();
 
        private Object category;
 
        public Object getAttribute(String name) {
            return attributes.get(name);
        }
 
        public void setAttribute(String name, Object value) {
            attributes.put(name, value);
        }
 
        public Object getCategory() {
            return category;
        }
 
        public void setCategory(Object category) {
            this.category = category;
        }
 
        public String toString() {
            return attributes.toString();
        }
 
    }
 
    /**
     * ����������Ҷ��㣩���������е�ÿ����Ҷ��㶼������һ�þ�����
     * ÿ����Ҷ������һ����֧���ԺͶ����֧����֧���Ե�ÿ��ֵ��Ӧһ����֧���÷�֧������һ���Ӿ�����
     */
    static class Tree {
 
        private String attribute;
 
        private Map<Object, Object> children = new HashMap<Object, Object>();
 
        public Tree(String attribute) {
            this.attribute = attribute;
        }
 
        public String getAttribute() {
            return attribute;
        }
 
        public Object getChild(Object attrValue) {
            return children.get(attrValue);
        }
 
        public void setChild(Object attrValue, Object child) {
            children.put(attrValue, child);
        }
 
        public Set<Object> getAttributeValues() {
            return children.keySet();
        }
 
    }
 
}
