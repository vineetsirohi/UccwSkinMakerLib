package lib.uccw_skin_maker.vasudev.in.uccwskinmakerlib;

import org.apache.commons.lang3.StringUtils;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.net.Uri;
import android.util.Log;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;


public class AssetsProvider extends ContentProvider {

//    public static final Uri CONTENT_URI =
//            Uri.parse("content://in.vasudev.example_uccw_skin.assets_provider");
//
//    public static final String COLUMN_NAME = "nameString";
//
//    public static final String COLUMN_IMAGE = "imageUri";

    private String[] mNames;

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        throw new UnsupportedOperationException("This operation is not supported");
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        throw new UnsupportedOperationException("This operation is not supported");
    }

    @Override
    public boolean onCreate() {
        Context context = getContext();
        AssetManager assetManager = context.getAssets();

        try {
            mNames = assetManager.list("");

            Log.d("UCCW example skin",
                    "in.vasudev.example.uccwskin.AssetsProvider.onCreate" + ": assets list: "
                            + Arrays.toString(mNames)
            );

            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
            String[] selectionArgs, String sortOrder) {
        MatrixCursor cursor = new MatrixCursor(projection);
        for (int i = 0; i < mNames.length; i++) {
            MatrixCursor.RowBuilder builder = cursor.newRow();
            builder.add(mNames[i]);
        }
        return cursor;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
            String[] selectionArgs) {
        throw new UnsupportedOperationException("This operation is not supported");
    }

    @Override
    public AssetFileDescriptor openAssetFile(Uri uri, String mode) throws
            FileNotFoundException {
        String path = getPath(uri); //uri.getLastPathSegment();
        Log.d("AssetsProvider",
                "in.vasudev.example.uccwskin.AssetsProvider. openAssetFile" + ": " + path);

        // Return the appropriate asset for the requested item
        if (path != null && !path.equals("")) {
            try {
                return getContext().getAssets().openFd(path);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    private String getPath(Uri uri) {
        Log.d("AssetsProvider", "in.vasudev.example.uccwskin.AssetsProvider.getPath" + ": path " + uri.getPath());

        String authority = new StringBuilder()
                .append("content://")
                .append(getContext().getPackageName())
                .append(".uccw_skin_assets_provider/")
                .toString();
        return StringUtils.removeStart(uri.getPath(), "/");
    }
}