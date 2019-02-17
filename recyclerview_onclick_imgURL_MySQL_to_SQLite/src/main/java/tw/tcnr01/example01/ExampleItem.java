package tw.tcnr01.example01;

public class ExampleItem {
    private  String mImageResource;
    private String mText1;
    private String mText2;

    public ExampleItem(String imgURL,String text1 ,String text2){
        mImageResource = imgURL;
        mText1 = text1;
        mText2 = text2;
    }

    public void changeText1(String text){
        mText1 = text;
    }

    public String getImageResource(){
        return mImageResource;
    }

    public  String getText1(){
        return mText1;
    }

    public  String getText2(){
        return mText2;
    }
}
