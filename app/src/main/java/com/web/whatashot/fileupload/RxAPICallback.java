package com.web.whatashot.fileupload;

public interface RxAPICallback<P> {
    void onSuccess(P t);

    void onFailed(Throwable throwable);
}

