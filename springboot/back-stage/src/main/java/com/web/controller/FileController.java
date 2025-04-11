package com.web.controller;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.web.annotation.IgnoreAuth;
import com.web.domain.Config;
import com.web.exception.BusinessException;
import com.web.service.ConfigService;
import com.web.utils.Result;
import org.apache.commons.io.FileUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

/**
 * 上传文件映射表
 */
@RestController
@RequestMapping("file")
@SuppressWarnings({"unchecked","rawtypes"})
public class FileController{
	@Resource
	private ConfigService configService;


	/**
	 * 上传文件
	 * @throws Exception
	 */
	@IgnoreAuth
	@RequestMapping("/upload")
	public Result upload(@RequestParam("file") MultipartFile file, String type) throws Exception {
		if (file.isEmpty()) {
			throw new BusinessException("上传文件不能为空");
		}
		String fileExt = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".")+1);
		File path = new File(ResourceUtils.getURL("classpath:static").getPath());
		if(!path.exists()) {
			path = new File("");
		}
		File upload = new File(path.getAbsolutePath().replace("\\target\\classes\\static",""),"src/main/resources/static/upload/");
		if(!upload.exists()) {
			upload.mkdirs();
		}
		String fileName = new Date().getTime()+"."+fileExt;
		File dest = new File(upload.getAbsolutePath()+"/"+fileName);
		file.transferTo(dest);
		if(StrUtil.isNotBlank(type) && type.equals("1")) {
			Config config = configService.getOne(Wrappers.lambdaQuery(Config.class).eq(Config::getName, "faceFile"));
			if(config==null) {
				config = new Config();
				config.setName("faceFile");
				config.setValue(fileName);
			} else {
				config.setValue(fileName);
			}
			configService.saveOrUpdate(config);
		}
		return Result.success(fileName);
	}

	/**
	 * 下载文件
	 */
	@IgnoreAuth
	@RequestMapping("/download")
	public ResponseEntity<byte[]> download(@RequestParam String fileName) {
		try {
			File path = new File(ResourceUtils.getURL("classpath:static").getPath());
			if(!path.exists()) {
				path = new File("");
			}
			File upload = new File(path.getAbsolutePath(),"/upload/");
			if(!upload.exists()) {
				upload.mkdirs();
			}
			File file = new File(upload.getAbsolutePath()+"/"+fileName);
			if(file.exists()){
				HttpHeaders headers = new HttpHeaders();
				headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
				headers.setContentDispositionFormData("attachment", fileName);
				return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file),headers, HttpStatus.CREATED);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return new ResponseEntity<byte[]>(HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
