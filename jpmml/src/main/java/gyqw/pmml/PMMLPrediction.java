package gyqw.pmml;

import org.apache.http.HttpHost;
import org.dmg.pmml.FieldName;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.jpmml.evaluator.*;

import java.io.File;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class PMMLPrediction {

    public static void main(String[] args) throws Exception {
        String appId = "NYB01-201113-440553";
        PMMLPrediction demo = new PMMLPrediction();
        Evaluator evaluator = demo.initModel();
        demo.predict(evaluator,
                demo.getMapping(),
                demo.getDataByAppId(appId));
    }

    private Evaluator initModel() throws Exception {
        String filePath = "/home/fred/git/study-java/jpmml/src/main/resources/CapitalDecisionTree.pmml";
        File file = new File(filePath);
        Evaluator evaluator = new LoadingModelEvaluatorBuilder()
                .load(file)
                .build();
        evaluator.verify();
        return evaluator;
    }

    private void predict(Evaluator evaluator, Map<String, String> mapping, Map<String, Object> data) {
        // 获取模型定义的特征
        List<? extends InputField> inputFields = evaluator.getInputFields();
        System.out.println("模型的特征是：" + inputFields);
        // 获取模型定义的目标名称
        List<? extends TargetField> targetFields = evaluator.getTargetFields();
        System.out.println("目标字段是：" + targetFields);

        // 好，下面将json转成evaluator要求的map格式，其实就是对key和value再做一层包装而已
        Map<FieldName, FieldValue> arguments = new LinkedHashMap<>();
        for (InputField inputField : inputFields) {
            FieldName inputName = inputField.getName();
            String name = mapping.get(inputName.getValue());
            Object rawValue = data.get(name);
            FieldValue inputValue = inputField.prepare(rawValue);
            arguments.put(inputName, inputValue);
        }

        // 得到特征数据后就是预测了
        Map<FieldName, ?> results = evaluator.evaluate(arguments);
        Map<String, ?> resultRecord = (Map<String, ?>) EvaluatorUtil.decode(results);
        Integer y = (Integer) resultRecord.get("y");

        // 打印结果会更加了解其中的封装过程
        System.out.println("预测结果：");
        System.out.println(results);
        System.out.println(resultRecord);
        System.out.println(y);
    }

    private Map<String, String> getMapping() {
        String[] fieldArray = FieldMapModel.fields.split(",");
        Map<String, String> mapping = new HashMap<>();
        for (int i = 0; i < fieldArray.length; i++) {
            mapping.put("x" + (i + 1), fieldArray[i]);
        }
        return mapping;
    }

    private Map<String, Object> getDataByAppId(String appId) throws Exception {
        RestHighLevelClient client = new RestHighLevelClient(
                RestClient.builder(new HttpHost("localhost", 9200, "http"))
        );
        GetRequest getRequest = new GetRequest("app-info-index", appId);
        GetResponse getResponse = client.get(getRequest, RequestOptions.DEFAULT);
        client.close();
        return getResponse.getSourceAsMap();
    }
}
