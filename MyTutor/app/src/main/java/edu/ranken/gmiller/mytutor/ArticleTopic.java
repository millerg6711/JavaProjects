package edu.ranken.gmiller.mytutor;

public enum ArticleTopic {
    JAVA(R.string.topic_java), ANDROID(R.string.topic_android), WEBDEV(R.string.topic_webdev);

    private final int mResourceId;

    private ArticleTopic(final int resourceId) {
        mResourceId = resourceId;
    }

    public int getResourceId() {
        return mResourceId;
    }
}
