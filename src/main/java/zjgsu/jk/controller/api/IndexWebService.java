/**
 * 
 */
package zjgsu.jk.controller.api;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;





import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import zjgsu.jk.dao.AccountRepository;
import zjgsu.jk.dao.ClassificationRepository;
import zjgsu.jk.dao.CourseClasRepository;
import zjgsu.jk.dao.CourseRepository;
import zjgsu.jk.dao.UserRepository;
import zjgsu.jk.model.Account;
import zjgsu.jk.model.Classification;
import zjgsu.jk.model.Course;
import zjgsu.jk.model.CourseClas;
import zjgsu.jk.model.User;
import zjgsu.jk.service.AbstractService;

/**
 * @author zby
 * 
 *
 */
@Controller
@RequestMapping("/api")
public class IndexWebService extends AbstractService {
	
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private ClassificationRepository classificationRepository;
	@Autowired
	private CourseRepository courseRepository;
	@Autowired
	private CourseClasRepository courseclasRepository;
	@Autowired
	private AccountRepository accountRepository;
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	private static String Url = "http://106.ihuyi.cn/webservice/sms.php?method=Submit";
	@RequestMapping(value="/user.json",method=RequestMethod.GET,produces=MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public  List<User> getUser(){
		System.out.println("hahah");
		return this.userRepository.findAll();
	}
	
	@RequestMapping(value="/childclassification.json",method=RequestMethod.GET,produces=MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public HashMap<String, Object> getClassificationCourse(@RequestParam("id") Long id){
		Classification classificationKey = this.classificationRepository.findById(id); 
		List<Classification> classificationList = this.classificationRepository.findByParent(classificationKey);
		int i,j;
		int listLength = classificationList.size();
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("listLength", listLength);
		map.put("List", classificationList);
		for(i=0; i<listLength; i++)
		{
			List<Course> courseList = new ArrayList<Course>() ;
			List<CourseClas> courseclasList = this.courseclasRepository.findByClassification(classificationList.get(i));
			for(j=0; j<courseclasList.size(); j++)
			{
				courseList.add(j, courseclasList.get(j).getCourse());	
			}
			map.put("Title"+Long.toHexString(courseclasList.get(i).getClassification().getId()), courseList);
		}
//		JSONObject json = JSONObject.fromObject(map);
//		System.out.rintln(json);
		return map; 
	}

	@RequestMapping(value="/courseinfo.json",method=RequestMethod.GET,produces=MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Course getCourseInfo(@RequestParam("id") Long id){
		Course course = this.courseRepository.findById(id);
		return course;
	}
	
	@RequestMapping(value="/userinfo.json",method=RequestMethod.GET,produces=MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public User getUserInfo(@RequestParam("id") Long id){
		User user = this.userRepository.findById(id);
		return user;
	}
	
	@RequestMapping(value="/login",method=RequestMethod.GET,produces=MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	//URL：localhost:8080/web-core/api/login?username=XXX&password=XXX
	public boolean login(@RequestParam("username") String username
			,@RequestParam("password")String password){
		Account  account = this.accountRepository.findByUsername(username);
		return passwordEncoder.matches(password, account.getPassword());
	}
	
	@RequestMapping(value="/pin",method=RequestMethod.GET,produces=MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	//URL：localhost:8080/web-core/api/register?username=XXX&password=XXX&captcha=XXX	
	public void captcha(){ 
		HttpClient client = new HttpClient(); 
		PostMethod method = new PostMethod(Url); 
			
		//client.getParams().setContentCharset("GBK");		
		client.getParams().setContentCharset("UTF-8");
		method.setRequestHeader("ContentType","application/x-www-form-urlencoded;charset=UTF-8");

		
		int mobile_code = (int)((Math.random()*9+1)*100000);

		//System.out.println(mobile);
	    String content = new String("您的验证码是：" + mobile_code + "。请不要把验证码泄露给其他人。"); 
		NameValuePair[] data = {//�ύ����
			    new NameValuePair("account", "cf_zhengboyi"), 
			    new NameValuePair("password", "64226641"), //�������ʹ�����������ʹ��32λMD5����
			    //new NameValuePair("password", util.StringUtil.MD5Encode("����")),
			    new NameValuePair("mobile", "15757129731"), 
			    new NameValuePair("content", content),
		};
		
		method.setRequestBody(data);		
		
		
		try {
			client.executeMethod(method);	
			
			String SubmitResult =method.getResponseBodyAsString();
					
			//System.out.println(SubmitResult);

			Document doc = DocumentHelper.parseText(SubmitResult); 
			Element root = doc.getRootElement();


			String code = root.elementText("code");	
			String msg = root.elementText("msg");	
			String smsid = root.elementText("smsid");	
			
			
			System.out.println(code);
			System.out.println(msg);
			System.out.println(smsid);
						
			 if("2".equals(code)){
				System.out.println("�����ύ�ɹ�");
			}
			
		} catch (HttpException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		
}
	
	@RequestMapping(value="/register",method=RequestMethod.GET,produces=MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	//URL：localhost:8080/web-core/api/register?username=XXX&password=XXX&captcha=XXX	
	public boolean register(@RequestParam("username") String username
			,@RequestParam("password")String password,
			@RequestParam("captcha")String captcha){
		Account  account = this.accountRepository.findByUsername(username);
		return passwordEncoder.matches(password, account.getPassword());
	}
}
