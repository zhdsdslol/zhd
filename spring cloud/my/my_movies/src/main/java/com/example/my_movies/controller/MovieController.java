package com.example.my_movies.controller;


import com.example.my_movies.help.RedisUtils;
import com.example.my_movies.models.DianzanEntity;
import com.example.my_movies.models.MoviesEntity;
import com.example.my_movies.models.ResultData;
import com.example.my_movies.service.MovieServiceImpl;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.exception.CosClientException;
import com.qcloud.cos.exception.CosServiceException;
import com.qcloud.cos.model.PutObjectRequest;
import com.qcloud.cos.model.PutObjectResult;
import com.qcloud.cos.region.Region;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.util.ClassUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;
import ws.schild.jave.MultimediaInfo;
import ws.schild.jave.MultimediaObject;
@RestController
@RequestMapping("/movie")
/*@CrossOrigin(allowCredentials = "true",allowedHeaders = "*")*/
public class MovieController extends  BaseController{
    String secretId = "AKIDJR275tRkvrbxESwEj24rPp55pIYYorFR";
    String secretKey = "7yZy84stJWXJq8FAwVpwoRp051lHaeT4";
    private static final Logger log = LogManager.getLogger(MovieController.class.getName());

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private MovieServiceImpl movieService;
    @Autowired
    private RedisUtils redisUtils;

    /**
     * @return 测试用
     */
    @HystrixCommand(fallbackMethod = "fallbacktest")
    @RequestMapping("/test")
    public String test(){
        String response = restTemplate.getForObject("http://server-users/user/usertest", String.class);
        return response;
    }
    public String fallbacktest(){
        return  "调用错误";
    }
    @RequestMapping("/movietest")
    public String movietest(){
        return "user调用movie成功！";
    }


    /**
     * 点赞
     * @param userid
     * @param movieid
     * @return
     */
    @RequestMapping("/dianzan")
    public ResultData dianzan(Integer userid,Integer movieid){
        if(userid==null||userid==0||movieid==null||movieid==0){
            return super.result(false,"点赞失败！",null);
        }
        movieService.dianzan(null,movieid,null);
        String s = redisUtils.get(userid + "");
        if(isnull(s)){
            redisUtils.set(userid + "",movieid+",");
            return super.Success(movieid);
        }
        if(s.split(",").length>=1){
            movieService.dianzan(userid, null, s);
            redisUtils.delete(userid+"");
            return super.Success(movieid);
        }
        redisUtils.set(userid + "",s+movieid+",");
        return super.Success(movieid);
    }

    /**
     * 查询是否点赞
     * @param userid
     * @param movieid
     * @return
     */
    @RequestMapping("/havedianzan")
    public ResultData havedianzan(Integer userid,Integer movieid){
        DianzanEntity finddianzan = null;
        finddianzan = movieService.finddianzan(userid);
        String movieids="";
        if(finddianzan!=null&&!isnull(finddianzan.getMovieid())){
             movieids = finddianzan.getMovieid();
        }
        movieids = redisUtils.get(userid + "")+movieids;
        System.out.println(movieids);
        if(isnull(movieids)){
            return super.result(false,"可以点赞",null);
        }
        String[] split = movieids.split(",");
        for (String s : split) {
            if(s.equals(movieid+"")){
                return super.result(true,"已点赞！",null);
            }
        }
        return super.result(false,"可以点赞",null);
    }

    /**
     * 获取视频信息
     * @param id
     * @return
     */
    @RequestMapping("/getmovie")
    public ResultData  getmovie(Integer id){
        MoviesEntity getmovie = movieService.getmovie(id);
        return  getmovie==null?super.result(false,"未找到视频！",null):super.Success(getmovie);
    }
    /**
     * @param movie  添加视频
     * @return
     */
    @RequestMapping("/addmovie")
    public ResultData addmv(MoviesEntity movie){
        try {
            movie.setAddtime(new Timestamp(System.currentTimeMillis()));
            movie.setDianzan(0);
            movie.setTalk(0);
            movie.setSee(0);
            movieService.AddMv(movie);
            return super.Success(movie);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("视频保存错误",e);
            return super.Error(movie);
        }
    }

    /**
     * @return 查询视频列表
     */
    @RequestMapping("/querymovie")
    public ResultData querymovie(String type, String name,int pagesize,int number){
        // spring boot 2.0推荐写法
        Pageable pageable = PageRequest.of(number-1,pagesize, Sort.Direction.DESC,"addtime");
        if(isnull(type)&&isnull(name)){
            return super.Success( movieService.FindMv(pageable));
        }else if(!isnull(name)){
            name="%"+name+"%";
        }
        if(!isnull(type)&&!isnull(name)){
            return super.Success( movieService.FindByTypeAndName(type,name,pageable));
        }
        if(isnull(type)){
            return super.Success( movieService.FindByName(name,pageable));
        }
        return super.Success( movieService.FindByType(type,pageable));
    }

    /**
     * 图片、视频上传
     */
    @RequestMapping("/uploadVideo")
    public ResultData uploadVideo(MultipartFile multipartFile){
        if(multipartFile!=null){
            //获取前端上传的文件名称
            String multifilename = multipartFile.getOriginalFilename();
            //取文件名下标，给文件重命名的时候使用
            String suffix = multifilename.substring(multifilename.indexOf("."));
            if(!".mp4".equals(suffix)&&!".jpg".equals(suffix)&&!".jpeg".equals(suffix)){
                return super.result(false,"文件格式不正确！",null);
            }
            //取一个随机id给文件重命名使用
            String uuid = System.currentTimeMillis()+"";
            //你的接收的文件新的名字
            String filename = uuid+suffix;
            //获取项目的绝对路径
//            String path = ClassUtils.getDefaultClassLoader().getResource("").getPath();
            /*Resource resource = new ClassPathResource("");
            String path = null;
            try {
                path = resource.getFile().getAbsolutePath();
            } catch (IOException e) {
                return super.result(false,"文件未能成功发送！",null);
            }*/
            //在项目新建一个 你重新生成名称的文件
            /*File file = new File(path+"\\files");
            if(!file.exists()){
                file.mkdir();
            }
            File upfile = new File(file,filename);*/
            //将接收的到的 multipartFile 类型的文件 转为 file
            File file = new File(ClassUtils.getDefaultClassLoader().getResource("").getPath()+"\\files");
            System.out.println(ClassUtils.getDefaultClassLoader().getResource("").getPath());
            if(!file.exists()){
                file.mkdir();
            }
            file=new File(file,filename);
            try {
                multipartFile.transferTo(file);
            } catch (IOException e) {
                return super.result(false,"文件上传出错啦！",null);
            }
            if(upyun(file,filename)){
                //获取接收到的并存在项目本地的文件，这样你就可以拿着这个文件随意处理啦
//            String filePath = upfile.getAbsolutePath();
                    if(".mp4".equals(suffix)){
                        Map result = new HashMap();
                        result.put("path",filename);
                        try {
                            result.put("time",getmvtime(file));
                        } catch (Exception e) {
                            e.printStackTrace();
                            return super.Error("获取视频时长出错啦!");
                        }
                        file.delete();
                        return super.Success(result) ;
                    }
                        file.delete();
                    return super.Success(filename) ;
            }else {
                return super.result(false,"文件未能成功发送到腾讯服务器！",null);
            }
            }
            return super.result(false,"上传的文件为空！",null);
    }
    //获取视频时长
    private String getmvtime(File file) throws Exception{
                MultimediaObject instance = new MultimediaObject(file);
                MultimediaInfo result = instance.getInfo();
                long ls = result.getDuration() / 1000;
                String ss = ls%3600%60+"";
                String hour = ls/3600==0?null:ls/3600+":";
                String min = ls%3600/60==0?"00:":ls%3600/60+":";
                return hour==null?min+ss:hour+min+ss;
    }

    //上传文件服务器腾讯对象存储
    public boolean upyun(File file,String filename){
        COSCredentials cred = new BasicCOSCredentials(secretId, secretKey);
        // 2 设置 bucket 的区域, COS 地域的简称请参照 https://cloud.tencent.com/document/product/436/6224
        // clientConfig 中包含了设置 region, https(默认 http), 超时, 代理等 set 方法, 使用可参见源码或者常见问题 Java SDK 部分。
        Region region = new Region("ap-guangzhou");
        ClientConfig clientConfig = new ClientConfig(region);
        // 3 生成 cos 客户端。
        COSClient cosClient = new COSClient(cred, clientConfig);
        try {
            // 指定要上传的文件
            File localFile = file;
            // 指定要上传到的存储桶
            String bucketName = "zhdtone-1259206032";
            // 指定要上传到 COS 上对象键
            String key = filename;
            PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, key, localFile);
            PutObjectResult putObjectResult = cosClient.putObject(putObjectRequest);
            return true;
        } catch (CosServiceException serverException) {
            serverException.printStackTrace();
        } catch (CosClientException clientException) {
            clientException.printStackTrace();
        }finally {
            cosClient.shutdown();
        }
        return false;
    }
}
