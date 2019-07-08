package MyUtil;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.Arrays;
import java.util.List;

import org.springframework.util.ResourceUtils;
import org.tensorflow.DataType;
import org.tensorflow.Graph;
import org.tensorflow.Output;
import org.tensorflow.Session;
import org.tensorflow.Tensor;
import org.tensorflow.types.UInt8;
import vo.FoodRank;

public class ModelPredict {//TODO 优化防止读模型时内存溢出

    //调用的API
    public static List<FoodRank> recognize(byte[] picture){

        String base = null;
        try {
            base = ResourceUtils.getFile("classpath:model").getPath();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        System.out.println("图片前十一位byte: ");
        for (int n = 0;n < 11;n++){
            int v = picture[n] & 0xFF;
            String hv = Integer.toHexString(v);
            System.out.print(hv + "  ");
        }
        System.out.println(base);

//        String modelDir = curDir + "\\src\\main\\resources";

        //测试用的图片
//        String imageFile = curDir + "\\src\\main\\resources\\test_image.jpg";

//        String labelDir =  curDir + "\\src\\main\\resources";

        String modelDir = base;
        String labelDir = base;

        byte[] graphDef = readAllBytesOrExit(Paths.get(modelDir, "frozen_graph.pb"));
        List<String> labels =
                readAllLinesOrExit(Paths.get(labelDir, "labels.txt"));
//        byte[] imageBytes = readAllBytesOrExit(Paths.get(imageFile));


        List<FoodRank> foodRanks = null;
        //读取byte 数组图片进去
        try {

            Tensor<Float> image = constructAndExecuteGraphToNormalizeImage(picture);

            //开始预测
            float[] labelProbabilities = executeInceptionGraph(graphDef, image);

            foodRanks = fiveIndex(labelProbabilities,labels);

            for (FoodRank f : foodRanks){

                System.out.println(
                        String.format("BEST MATCH: %s (%.2f%% likely)",
                                f.getFoodname(),
                                f.getProbability() * 100f));
            }

        }catch (Exception e){
            e.printStackTrace();
        }

        return foodRanks;
    }

    public static void main(String[] args) {

//        String curDir = System.getProperty("use.dir");
//
//        System.out.println(curDir);
//
//        String modelDir = curDir + "\\src\\main\\resources";
//        String imageFile = curDir + "\\src\\main\\resources\\test_image.jpg";
//        String labelDir =  curDir + "\\src\\main\\resources";
//        byte[] graphDef = readAllBytesOrExit(Paths.get(modelDir, "frozen_graph.pb"));
//        List<String> labels =
//                readAllLinesOrExit(Paths.get(labelDir, "labels.txt"));
//        byte[] imageBytes = readAllBytesOrExit(Paths.get(imageFile));
//
//
//        try (Tensor<Float> image = constructAndExecuteGraphToNormalizeImage(imageBytes)) {
//
//            float[] labelProbabilities = executeInceptionGraph(graphDef, image);
//
//            List<FoodRank> foodRanks = fiveIndex(labelProbabilities,labels);
//
//            for (FoodRank f : foodRanks){
//
//                System.out.println(
//                        String.format("BEST MATCH: %s (%.2f%% likely)",
//                                f.getFoodname(),
//                                f.getProbability() * 100f));
//            }
//
//        }

    }

    private static Tensor<Float> constructAndExecuteGraphToNormalizeImage(byte[] imageBytes) {
        try (Graph g = new Graph()) {
            GraphBuilder b = new GraphBuilder(g);
// Some constants specific to the pre-trained model at:
// https://storage.googleapis.com/download.tensorflow.org/models/inception5h.zip
//
// - The model was trained with images scaled to 224x224 pixels.
// - The colors, represented as R, G, B in 1-byte each were converted to
//   float using (value - Mean)/Scale.
            final int H = 299;
            final int W = 299;
            final float mean = 128f;
            final float scale = 128f;
// Since the graph is being constructed once per execution here, we can use a constant for the
// input image. If the graph were to be re-used for multiple input images, a placeholder would
// have been more appropriate.
            final Output<String> input = b.constant("input", imageBytes);
            final Output<Float> output =
                    b.div(
                            b.sub(
                                    b.resizeBilinear(
                                            b.expandDims(
                                                    b.cast(b.decodeJpeg(input, 3), Float.class),
                                                    b.constant("make_batch", 0)),
                                            b.constant("size", new int[]{H, W})),
                                    b.constant("mean", mean)),
                            b.constant("scale", scale));
            try (Session s = new Session(g)) {
                return s.runner().fetch(output.op().name()).run().get(0).expect(Float.class);
            }
        }
    }

    private static float[] executeInceptionGraph(byte[] graphDef, Tensor<Float> image) {
        try (Graph g = new Graph()) {
            g.importGraphDef(graphDef);
            try (Session s = new Session(g);
                 Tensor<Float> result =
                         s.runner().feed("input", image).fetch("InceptionV3/Predictions/Reshape_1").run().get(0).expect(Float.class)) {
                final long[] rshape = result.shape();
                if (result.numDimensions() != 2 || rshape[0] != 1) {
                    throw new RuntimeException(
                            String.format(
                                    "Expected model to produce a [1 N] shaped tensor where N is the number of labels, instead it produced one with shape %s",
                                    Arrays.toString(rshape)));
                }
                int nlabels = (int) rshape[1];
                return result.copyTo(new float[1][nlabels])[0];
            }
        }
    }

    //找出前五个最大的
    private static List<FoodRank> fiveIndex(float[] probabilities,List<String> labels){
        List<FoodRank> foodRanks = new ArrayList<>();
        FoodRank foodRank = null;
        for (int n = 0;n < labels.toArray().length;n++){
            foodRank = new FoodRank(labels.get(n),probabilities[n]);
            foodRanks.add(foodRank);
        }

        Collections.sort(foodRanks);


        return foodRanks.subList(0,5);
    }

    private static int maxIndex(float[] probabilities) {
        int best = 0;
        for (int i = 1; i < probabilities.length; ++i) {
            if (probabilities[i] > probabilities[best]) {
                best = i;
            }
        }
        return best;
    }

    private static byte[] readAllBytesOrExit(Path path) {
        try {
            return Files.readAllBytes(path);
        } catch (IOException e) {
            System.err.println("Failed to read [" + path + "]: " + e.getMessage());
            System.exit(1);
        }
        return null;
    }

    private static List<String> readAllLinesOrExit(Path path) {
        try {
            return Files.readAllLines(path, Charset.forName("UTF-8"));
        } catch (IOException e) {
            System.err.println("Failed to read [" + path + "]: " + e.getMessage());
            System.exit(0);
        }
        return null;
    }

    // In the fullness of time, equivalents of the methods of this class should be auto-generated from
// the OpDefs linked into libtensorflow_jni.so. That would match what is done in other languages
// like Python, C++ and Go.

    static class GraphBuilder {
        GraphBuilder(Graph g) {
            this.g = g;
        }

        Output<Float> div(Output<Float> x, Output<Float> y) {
            return binaryOp("Div", x, y);
        }

        <T> Output<T> sub(Output<T> x, Output<T> y) {
            return binaryOp("Sub", x, y);
        }

        <T> Output<Float> resizeBilinear(Output<T> images, Output<Integer> size) {
            return binaryOp3("ResizeBilinear", images, size);
        }

        <T> Output<T> expandDims(Output<T> input, Output<Integer> dim) {
            return binaryOp3("ExpandDims", input, dim);
        }

        <T, U> Output<U> cast(Output<T> value, Class<U> type) {
            DataType dtype = DataType.fromClass(type);
            return g.opBuilder("Cast", "Cast")
                    .addInput(value)
                    .setAttr("DstT", dtype)
                    .build()
                    .<U>output(0);
        }

        Output<UInt8> decodeJpeg(Output<String> contents, long channels) {
            return g.opBuilder("DecodeJpeg", "DecodeJpeg")
                    .addInput(contents)
                    .setAttr("channels", channels)
                    .build()
                    .<UInt8>output(0);
        }

        <T> Output<T> constant(String name, Object value, Class<T> type) {
            try (Tensor<T> t = Tensor.<T>create(value, type)) {
                return g.opBuilder("Const", name)
                        .setAttr("dtype", DataType.fromClass(type))
                        .setAttr("value", t)
                        .build()
                        .<T>output(0);
            }
        }

        Output<String> constant(String name, byte[] value) {
            return this.constant(name, value, String.class);
        }

        Output<Integer> constant(String name, int value) {
            return this.constant(name, value, Integer.class);
        }

        Output<Integer> constant(String name, int[] value) {
            return this.constant(name, value, Integer.class);
        }

        Output<Float> constant(String name, float value) {
            return this.constant(name, value, Float.class);
        }

        private <T> Output<T> binaryOp(String type, Output<T> in1, Output<T> in2) {
            return g.opBuilder(type, type).addInput(in1).addInput(in2).build().<T>output(0);
        }

        private <T, U, V> Output<T> binaryOp3(String type, Output<U> in1, Output<V> in2) {
            return g.opBuilder(type, type).addInput(in1).addInput(in2).build().<T>output(0);
        }

        private Graph g;
    }
}
