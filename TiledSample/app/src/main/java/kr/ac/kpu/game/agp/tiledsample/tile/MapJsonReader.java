package kr.ac.kpu.game.agp.tiledsample.tile;

import android.content.res.Resources;
import android.util.JsonReader;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.util.ArrayList;

class MapJsonReader {
    private static final String TAG = MapJsonReader.class.getSimpleName();

    public TiledMap read(Resources res, int resId) {
        InputStream stream = res.openRawResource(resId);
        if (stream == null) {
            return null;
        }
        try {
            JsonReader reader = new JsonReader(new InputStreamReader(stream));
            return readMap(reader);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private TiledMap readMap(JsonReader reader) throws IOException {
        Log.v(TAG, "Reading map:");
        TiledMap map = new TiledMap();
        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (name.equals("layers")) {
                reader.beginArray();
                ArrayList<TiledLayer> layers = new ArrayList<>();
                while (reader.hasNext()) {
                    TiledLayer layer = readLayer(reader);
                    if (layer != null) {
                        layers.add(layer);
                    }
                }
                map.layers = layers;
                reader.endArray();
            } else if (name.equals("tilesets")) {
                reader.beginArray();
                ArrayList<TiledTileset> tilesets = new ArrayList<>();
                while (reader.hasNext()) {
                    tilesets.add(readTileset(reader));
                }
                map.tilesets = tilesets;
                reader.endArray();
            } else if (readProperty(map, name, reader)) {
            } else {
                reader.skipValue();
            }
        }
        return map;
    }

    private TiledTileset readTileset(JsonReader reader) throws IOException {
        Log.v(TAG, " Reading tileset:");
        TiledTileset tileset = new TiledTileset();
        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (readProperty(tileset, name, reader)) {
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return tileset;
    }

    private TiledLayer readLayer(JsonReader reader) throws IOException {
        Log.v(TAG, " Reading layer:");
        TiledLayer layer = new TiledLayer();
        reader.beginObject();
        String layerType = "";
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (name.equals("data")) {
                layer.data = readLayerData(reader);
                Log.d(TAG, "int[] : " + layer.data.length + " integers");
            } else if (name.equals("type")) {
                layerType = reader.nextString();
            } else if (readProperty(layer, name, reader)) {
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        if (!layerType.equals("tilelayer")) {
            return null;
        }
        return layer;
    }

    private int[] readLayerData(JsonReader reader) throws IOException {
        ArrayList<Integer> integers = new ArrayList<>();
        reader.beginArray();
        while (reader.hasNext()) {
            int value = reader.nextInt();
            integers.add(value);
        }
        reader.endArray();

        int[] ints = new int[integers.size()];
        for (int i = 0; i < ints.length; i++) {
            ints[i] = integers.get(i);
        }

        return ints;
    }

    private boolean readProperty(Object object, String name, JsonReader reader) throws IOException {
        try {
            Field field = object.getClass().getField(name);
            Class<?> type = field.getType();
            if (type == int.class) {
                int value = reader.nextInt();
                Log.d(TAG, "Int " + name + ": " + value + " - " + object);
                field.setInt(object, value);
            } else if (type == String.class) {
                String value = reader.nextString();
                Log.d(TAG, "String " + name + ": " + value + " - " + object);
                field.set(object, value);
            } else {
                Log.e(TAG, "Not handling " + name + ". type: " + type + " - " + object);
                return false;
            }
            return true;
        } catch (NoSuchFieldException e) {
            Log.e(TAG, "No field \"" + name + "\" in " + object);
//            e.printStackTrace();
            return false;
        } catch (IllegalAccessException e) {
//            e.printStackTrace();
            return false;
        }
    }
}
