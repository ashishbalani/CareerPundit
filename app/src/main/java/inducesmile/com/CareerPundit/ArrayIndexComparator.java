package inducesmile.com.CareerPundit;

import java.util.Comparator;

/**
 * Created by test on 21-03-2016.
 */
public class ArrayIndexComparator implements Comparator<Integer> {
    private final Integer[] code;
    public ArrayIndexComparator(Integer[] code){
        this.code=code;
    }

    public Integer[] createIndexArray(){
        Integer[] indexes=new Integer[code.length];
        for(int i=0;i<code.length;i++){
            indexes[i]=i;
        }
        return indexes;
    }
    @Override
    public int compare(Integer index1, Integer index2) {
        return code[index1].compareTo(code[index2]);
    }
}
