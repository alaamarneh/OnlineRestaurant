package com.am.onlinerestaurant.data.callback;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public class LiveResponse<T> {
    public static class Status{
        public final static int SUCCESS = 1;
        public final static int ERROR = 2;
        public final static int LOADING = 3;
    }
        @NonNull
        public final int status;
        @Nullable
        public final T data;
        @Nullable
        public final String message;

        public boolean isSuccess(){
            return status== Status.SUCCESS;
        }
        public boolean isLoading(){
            return status== Status.LOADING;
        }
        private LiveResponse(@NonNull int status, @Nullable T data, @Nullable String message) {
            this.status = status;
            this.data = data;
            this.message = message;
        }

        public static <T> LiveResponse<T> success(@NonNull T data) {
            return new LiveResponse<>(Status.SUCCESS, data, null);
        }

        public static <T> LiveResponse<T> error(String msg, T data) {
            return new LiveResponse<>(Status.ERROR, data, msg);
        }

        public static <T> LiveResponse<T> loading(@Nullable T data) {
            return new LiveResponse<>(Status.LOADING, data, null);
        }
    }