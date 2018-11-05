package com.example.jayand_zuch569.news;

import android.os.Parcel;
import android.os.Parcelable;

public class PageModel implements Parcelable {

    private String imageUrl, titleString, _60wordsContent, readMoreUrl;

    PageModel(String imageUrl_, String titleString_, String _60wordsContent_, String readMoreUrl_){
        this.imageUrl = imageUrl_;
        this.titleString = titleString_;
        this._60wordsContent = _60wordsContent_;
        this.readMoreUrl = readMoreUrl_;
    }

    String getImageUrl(){
        return this.imageUrl;
    }

    String getTitleString(){
        return titleString;
    }

    String get_60WordsContent(){
        return _60wordsContent;
    }

    String getReadMoreUrl(){
        return readMoreUrl;
    }

    private PageModel(Parcel in) {
        imageUrl = in.readString();
        titleString = in.readString();
        _60wordsContent = in.readString();
        readMoreUrl = in.readString();
    }

    public static final Creator<PageModel> CREATOR = new Creator<PageModel>() {
        @Override
        public PageModel createFromParcel(Parcel in) {
            return new PageModel(in);
        }

        @Override
        public PageModel[] newArray(int size) {
            return new PageModel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(imageUrl);
        dest.writeString(titleString);
        dest.writeString(_60wordsContent);
        dest.writeString(readMoreUrl);
    }
}
