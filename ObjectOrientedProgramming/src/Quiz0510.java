public class Quiz0510 {
    public static void main(String[] args){
        DictionaryApp.main(new String[0]);
    }
}

abstract class PairMap {
    protected String keyArray[];    // key들을 저장하는 배열
    protected String valueArray[];  // value들을 저장하는 배열
    abstract String get(String key);    // key값을 가진 value 리턴. 없으면 null 리턴
    abstract void put(String key, String value);    // key와 value를 쌍으로 저장. 기존에 key가 있으면 값을 value로 수정.
    abstract String delete(String key); // key 값을 가진 아이템(value와 함께) 삭제. 삭제된 value 값 리턴
    abstract int length();  // 현재 저장된 아이템의 개수 리턴
}
class Dictionary extends PairMap {
    private int cnt = 0;
    private int lastIdx = 0;
    public Dictionary(int n){
        this.keyArray = new String[n];
        this.valueArray = new String[n];
    }

    private int indexOf(String key){
        for(int i=0; i<lastIdx; i++){
            if(keyArray[i].equals(key)){
                return i;
            }
        }
        return -1;
    }

    @Override
    String get(String key) {
        int idx = indexOf(key);
        if(idx >=0){
            return valueArray[idx];
        } else{
            return null;
        }
    }

    @Override
    void put(String key, String value) {
        if(lastIdx == keyArray.length){
            System.out.println("용량초과");
            return;
        }
        int idx = indexOf(key);
        if(idx >= 0){
            valueArray[idx] = value;
        } else {
            keyArray[lastIdx] = key;
            valueArray[lastIdx] = value;
            lastIdx++;
            cnt++;
        }
    }

    @Override
    String delete(String key) {
        int idx = indexOf(key);
        String res = null;
        if(idx >= 0){
            res = valueArray[idx];
            valueArray[idx] = null;
            valueArray[idx] = null;
            cnt--;
        }
        return res;
    }

    @Override
    int length() {
        return cnt;
    }
}

class DictionaryApp{
    public static void main(String[] args){
        Dictionary dic = new Dictionary(10);
        dic.put("황기태", "자바");
        dic.put("이재문", "파이선");
        dic.put("이재문", "C++");  // 이재문의 값을 C++로 수정
        System.out.println("이재문의 값은 " + dic.get("이재문")); // 이재문 아이템 출력
        System.out.println("황기태의 값은 " +  dic.get("황기태")); // 황기태 아이템 출력
        dic.delete("황기태");  // 황기태 아이템 삭제
        System.out.println("황기태의 값은 " + dic.get("황기태"));
    }
}