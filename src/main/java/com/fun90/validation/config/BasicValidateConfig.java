package com.fun90.validation.config;

import com.fun90.validation.IValidator;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.*;

/**
 * 基础验证配置
 *
 * @author fun90
 * @version V1.00 2015-7-20
 */
public class BasicValidateConfig implements IValidateConfig {
    private static final Logger logger = LoggerFactory.getLogger(BasicValidateConfig.class);

    private final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
    private Document document;             // 主配置文件dom
    private String resource;               // 主配置文件路径
    private Configuration configuration;

    public BasicValidateConfig() {
        this("validation.xml");
    }

    public BasicValidateConfig(String resource) {
        super();
        this.resource = resource;
        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            String file = BasicValidateConfig.class.getResource("/" + resource).getFile();
            logger.debug("config: " + file);
            this.document = builder.parse(file);
        } catch (ParserConfigurationException e) {
            throw new RuntimeException(e.getMessage(), e);
        } catch (SAXException e) {
            throw new RuntimeException(e.getMessage(), e);
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    @Override
    public Configuration readConfiguration() {
        if (configuration == null) {
            configuration = new Configuration();
            this.configuration.setProperties(this.readProperties()); // 读取属性
            this.configuration.setValidators(this.readValidators()); // 读取验证器
            this.configuration.setGroups(this.readGroups());         // 读取组验证规则
        }
        return configuration;
    }

    private Properties readProperties() {
        logger.info("read properties from  " + this.resource);
        Properties properties = new Properties();

        NodeList propertiesList = this.document.getElementsByTagName("properties");
        if (propertiesList != null && propertiesList.getLength() != 0) {
            NodeList propertyList = ((Element) propertiesList.item(0)).getElementsByTagName("property");
            for(int i = 0; i < propertyList.getLength(); i++) {
                Element element = (Element) propertyList.item(i);
                String key = element.getAttribute("name");
                if(key == null || key.isEmpty()){
                    throw new RuntimeException("validation config error: the name of property is null or empty");
                }
                key = key.trim();
                String value = element.getAttribute("value");
                if(value == null || value.isEmpty()){
                    throw new RuntimeException("validation config error: the value of property is null or empty");
                }
                properties.put(key, value);
            }
        }

        return properties;
    }

    /**
     * 读rule元素
     *
     * @param ruleE
     * @return Rule
     */
    private void readRules(Element ruleE, List<Rule> rules) {
        // 解析rule元素的ref属性
        String rulesRef = ruleE.getAttribute("ref");
        if (rulesRef != null && rulesRef.length() > 0) {
            Element globalRulesE = this.getElementById("rules", rulesRef);
            NodeList rulesList = globalRulesE.getElementsByTagName("rule");
            for (int i = 0; i < rulesList.getLength(); i++) {
                Element globalRuleE = (Element) rulesList.item(i);
                readRules(globalRuleE, rules);
            }
        } else {
            // 解析rule元素的子元素param和其属性
            Rule rule = new Rule();
            NodeList params = ruleE.getElementsByTagName("param");
            for (int i = 0; i < params.getLength(); i++) {
                Element paramE = (Element) params.item(i);
                String pname = paramE.getAttribute("name").trim();
                String pvalue = paramE.getAttribute("value").trim();
                rule.addParameter(pname, pvalue);
            }

            // rule元素的属性name及message
            String rname = ruleE.getAttribute("name").trim();
            String rmessage = ruleE.getAttribute("message").trim();
            rule.setName(rname);
            rule.setMessage(rmessage);
            rules.add(rule);
        }

    }

    private Element getElementById(String eleName, String id) {
        NodeList nodeList = this.document.getElementsByTagName(eleName);
        for (int i = 0; i < nodeList.getLength(); i++) {
            Element element = (Element) nodeList.item(i);
            if (id.equals(element.getAttribute("id"))) {
                return element;
            }
        }
        return null;
    }

    /**
     * 读取验证器
     *
     * @return
     */
    private Map<String, IValidator> readValidators() {
        logger.info("read validators file, " + this.resource);
        Map<String, IValidator> validators = new HashMap<String, IValidator>();

        NodeList validatorList = this.document.getElementsByTagName("validator");
        if (validatorList != null) {
            for (int i = 0; i < validatorList.getLength(); i++) {
                Element validatorE = (Element) validatorList.item(i);
                String vname = validatorE.getAttribute("name").trim();
                String vclass = validatorE.getAttribute("class").trim();
                Validator v = new Validator();
                v.setName(vname);
                v.setClassName(vclass);
                try {
                    IValidator validator = this.instanceValidator(v);
                    validators.put(vname, validator);
                } catch (ClassNotFoundException e) {
                    //ignore
                } catch (InstantiationException e) {
                    //ignore
                } catch (IllegalAccessException e) {
                    //ignore
                }
            }
        }
        return validators;
    }

    /**
     * 读取组配置
     *
     * @return
     */
    private Map<String, List<Field>> readGroups() {
        logger.info("read main groups file , " + this.getResource());
        Map<String, List<Field>> groups = new HashMap<String, List<Field>>();

        //读取引入列表
        NodeList includeList = this.document.getElementsByTagName("include");
        if (includeList != null) {
            for (int i = 0; i < includeList.getLength(); i++) {
                Element includeE = (Element) includeList.item(i);
                String resource = includeE.getAttribute("resource");
                if (StringUtils.isBlank(resource))
                    continue;
                this.readGroups(groups, resource);
            }
        }
        this.readGroups(groups, this.document);
        return groups;
    }

    private void readGroups(Map<String, List<Field>> groups, String path) {
        logger.info("read include groups file , " + path);
        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(BasicValidateConfig.class.getResource("/") + path);

            //读取引入列表
            NodeList includeList = document.getElementsByTagName("include");
            if (includeList != null) {
                for (int i = 0; i < includeList.getLength(); i++) {
                    Element includeE = (Element) includeList.item(i);
                    String file = includeE.getAttribute("file");
                    if (StringUtils.isBlank(file))
                        continue;
                    this.readGroups(groups, file);
                }
            }
            this.readGroups(groups, document);
        } catch (ParserConfigurationException e) {
            logger.error(e.getMessage(), e);
        } catch (SAXException e) {
            logger.error(e.getMessage(), e);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
    }

    private void readGroups(Map<String, List<Field>> groups, Document document) {
        //读取Group
        NodeList groupList = document.getElementsByTagName("group");
        for (int i = 0; i < groupList.getLength(); i++) {
            Element groupE = (Element) groupList.item(i);
            String gid = groupE.getAttribute("id").trim();

            List<Field> fields = this.readFields(groupE);

            String extend = groupE.getAttribute("extend");
            if (extend != null && !extend.isEmpty()) {
                Element parent = this.getElementById("group", extend);
                if (parent == null)
                    throw new RuntimeException("group: " + gid + ", extend: " + extend + " is not found.");
                fields.addAll(readFields(parent));
            }
            groups.put(gid, fields);
        }
    }

    private List<Field> readFields(Element groupEle) {
        //读取字段
        List<Field> fields = new ArrayList<Field>();
        NodeList fieldList = groupEle.getElementsByTagName("field");
        for (int j = 0; j < fieldList.getLength(); j++) {
            Element fieldE = (Element) fieldList.item(j);
            String fname = fieldE.getAttribute("name").trim();
            Field field = new Field();
            field.setName(fname);

            //读取规则
            List<Rule> rules = new ArrayList<Rule>();
            NodeList ruleList = fieldE.getElementsByTagName("rule");
            for (int k = 0; k < ruleList.getLength(); k++) {
                Element ruleE = (Element) ruleList.item(k);
                readRules(ruleE, rules);
            }

            field.setRules(rules);
            fields.add(field);
        }
        return fields;
    }

    /**
     * 实例化验证器
     *
     * @param v 验证对象
     * @return 验证器
     * @throws ClassNotFoundException
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    @SuppressWarnings("unchecked")
    public IValidator instanceValidator(Validator v) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
        Class<IValidator> vclassor = (Class<IValidator>) Class.forName(v.getClassName());
        return vclassor.newInstance();
    }

    public String getResource() {
        return resource;
    }

    public void setResource(String resource) {
        this.resource = resource;
    }
}
