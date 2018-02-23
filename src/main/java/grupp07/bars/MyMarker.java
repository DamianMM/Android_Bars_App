package grupp07.bars;
public class MyMarker
{
    private String mLabel;
    private String mSnippet;
    private String mIcon;
    private String mFlickrTag;
    private Double mLatitude;
    private Double mLongitude;

    public MyMarker(String label, String snippet, String icon, Double latitude, Double longitude, String tag)
    {
        this.mLabel = label;
        this.mSnippet = snippet;
        this.mLatitude = latitude;
        this.mLongitude = longitude;
        this.mIcon = icon;
        this.mFlickrTag = tag;
    }

    public String getmLabel()
    {
        return mLabel;
    }

    public void setmLabel(String mLabel)
    {
        this.mLabel = mLabel;
    }

    public void setmSnippet(String mSnippet)
    {
        this.mSnippet = mSnippet;
    }

    public String getmSnippet()
    {
        return mSnippet;
    }

    public void setmFlickrTag(String tag)
    {
        this.mFlickrTag = tag;
    }

    public String getmFlickrTag()
    {
        return mFlickrTag;
    }

    public String getmIcon()
    {
        return mIcon;
    }

    public void setmIcon(String icon)
    {
        this.mIcon = icon;
    }

    public Double getmLatitude()
    {
        return mLatitude;
    }

    public void setmLatitude(Double mLatitude)
    {
        this.mLatitude = mLatitude;
    }

    public Double getmLongitude()
    {
        return mLongitude;
    }

    public void setmLongitude(Double mLongitude)
    {
        this.mLongitude = mLongitude;
    }
}