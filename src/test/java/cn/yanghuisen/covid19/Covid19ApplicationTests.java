package cn.yanghuisen.covid19;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ReUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpUtil;
import cn.yanghuisen.covid19.mapper.AddressMapper;
import cn.yanghuisen.covid19.mapper.CityMapper;
import cn.yanghuisen.covid19.mapper.DayDataMapper;
import cn.yanghuisen.covid19.pojo.Address;
import cn.yanghuisen.covid19.pojo.City;
import cn.yanghuisen.covid19.pojo.DayData;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

@SpringBootTest
class Covid19ApplicationTests {

    @Autowired
    private CityMapper cityMapper;
    @Autowired
    private DayDataMapper dayDataMapper;
    @Autowired
    private AddressMapper addressMapper;

    @Test
    void contextLoads() {
    }

    @Test
    void test(){
        long proId = 1514979369040113664L;
        List<City> cityList = cityMapper.findCityByProId(proId);
        String dateStr = DateUtil.format(DateUtil.yesterday() , "yyyy年MM月dd日");
        // 获取文章源码
        HttpRequest request = HttpUtil.createGet("https://mp.weixin.qq.com/s/OZGM-pNkefZqWr0IFRJj1g");
        String body = request.execute().body();
        // 正在表达式
        String regex = "<section data-id=\"72469\" data-tools=\"135编辑器\">(.*?)</section>";
        ArrayList<String> dataDetail = ReUtil.findAll(regex, body, 0, new ArrayList<String>());
        for (String str : dataDetail) {
            // 替换字符串
            str = str.replaceAll("</span><span style=\"font-size: 16px;color: inherit;font-family: mp-quote, -apple-system-font, BlinkMacSystemFont, Arial, sans-serif;\">","");
            String regex1 = "<span style=\"font-size: 16px;\">(.*?)</span>";
            ArrayList<String> itemDateDetail = ReUtil.findAll(regex1, str, 0, new ArrayList<String>());
            long cityId = -1;
            long id = IdUtil.getSnowflakeNextId();
            for (int i = 0; i < itemDateDetail.size(); i++) {
                String itemStr = itemDateDetail.get(i);
                itemStr = itemStr.replaceAll("例确诊病例","例本土确诊病例");
                itemStr = itemStr.replaceAll("例本土新冠肺炎确诊病例","例本土确诊病例");
                itemStr = itemStr.replaceAll("例新冠肺炎本土确诊病例","例本土确诊病例");
                if (i == 0){
                    String regex2 = "<span style=\"font-size: 16px;\">(.*?)，(.*?)新增(.*?)例本土确诊病例，新增(.*?)例本土无症状感染者，分别居住于：</span>";
                    if (ReUtil.isMatch(regex2,itemStr)){
                        List<String> allGroups = ReUtil.getAllGroups(Pattern.compile(regex2), itemStr);
                        // 时间
                        String date = allGroups.get(1);
                        // 地区名称
                        String name = allGroups.get(2);
                        // 确诊
                        String diagnosis = allGroups.get(3);
                        // 无症状
                        String asymptomatic = allGroups.get(4);
                        System.out.printf("%s: %s例确认病例, %s例无症状感染者%n",name,diagnosis,asymptomatic);
                        for (City city : cityList) {
                            if (city.getName().equals(name)) {
                                cityId = city.getId();
                                DayData dayData = new DayData();
                                dayData.setId(id);
                                dayData.setCityId(cityId);
                                dayData.setDate(DateUtil.parse(date,"yyyy年MM月dd日"));
                                dayData.setDiagnosis(Integer.valueOf(diagnosis));
                                dayData.setAsymptomatic(Integer.valueOf(asymptomatic));
                                // 删除数据
                                dayDataMapper.delDayData(dayData);
                                // 插入新的数据
                                dayDataMapper.addDayData(dayData);
                                // 删除数据
                                addressMapper.delAddress(cityId,DateUtil.parse(date,"yyyy年MM月dd日"));
                                break;
                            }
                        }
                    }
                }else {
                    Address address = new Address();
                    address.setId(IdUtil.getSnowflakeNextId());
                    address.setDayDataId(id);
                    address.setAddress(ReUtil.get(regex1, itemStr, 1).replaceAll("，", ""));
                    addressMapper.addAddress(address);
                }
            }
        }
    }

    @Test
    public void test2(){
        long id = 1514979369040113664L;
        City city = new City(id, "浦东新区");
        city.setId(IdUtil.getSnowflakeNextId());
        cityMapper.addCity(city);
        City city1 = new City(id, "黄浦区");
        city1.setId(IdUtil.getSnowflakeNextId());
        cityMapper.addCity(city1);
        City city2 = new City(id, "长宁区");
        city2.setId(IdUtil.getSnowflakeNextId());
        cityMapper.addCity(city2);
        City city3 = new City(id, "静安区");
        city3.setId(IdUtil.getSnowflakeNextId());
        cityMapper.addCity(city3);
        City city4 = new City(id, "徐汇区");
        city4.setId(IdUtil.getSnowflakeNextId());
        cityMapper.addCity(city4);
        City city5 = new City(id, "普陀区");
        city5.setId(IdUtil.getSnowflakeNextId());
        cityMapper.addCity(city5);
        City city6 = new City(id, "虹口区");
        city6.setId(IdUtil.getSnowflakeNextId());
        cityMapper.addCity(city6);
        City city7 = new City(id, "杨浦区");
        city7.setId(IdUtil.getSnowflakeNextId());
        cityMapper.addCity(city7);
        City city8 = new City(id, "闵行区");
        city8.setId(IdUtil.getSnowflakeNextId());
        cityMapper.addCity(city8);
        City city9 = new City(id, "宝山区");
        city9.setId(IdUtil.getSnowflakeNextId());
        cityMapper.addCity(city9);
        City city10 = new City(id, "嘉定区");
        city10.setId(IdUtil.getSnowflakeNextId());
        cityMapper.addCity(city10);
        City city11 = new City(id, "金山区");
        city11.setId(IdUtil.getSnowflakeNextId());
        cityMapper.addCity(city11);
        City city12 = new City(id, "松江区");
        city12.setId(IdUtil.getSnowflakeNextId());
        cityMapper.addCity(city12);
        City city13 = new City(id, "青浦区");
        city13.setId(IdUtil.getSnowflakeNextId());
        cityMapper.addCity(city13);
        City city14 = new City(id, "奉贤区");
        city14.setId(IdUtil.getSnowflakeNextId());
        cityMapper.addCity(city14);
        City city15 = new City(id, "崇明区");
        city15.setId(IdUtil.getSnowflakeNextId());
        cityMapper.addCity(city15);
    }

    @Test
    public void test3(){
        List<City> cityByProId = cityMapper.findCityByProId(1L);
        for (City city : cityByProId) {
            System.out.println(city);
        }
    }
    @Test
    public void test4(){
        // 获取雪花算法ID
        long id =IdUtil.getSnowflakeNextId();
        System.out.println(id);
    }

}
