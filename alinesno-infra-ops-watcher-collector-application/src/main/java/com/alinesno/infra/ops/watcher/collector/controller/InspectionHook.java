package com.alinesno.infra.ops.watcher.collector.controller;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import com.alinesno.infra.ops.watcher.collector.bean.InspectionDataDto;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONObject;

/**
 * 获取文件上传的配置
 * 
 */
@Slf4j
@RestController
public class InspectionHook {

	/**
	 * 接收多参数请求
	 * 
	 * @param pluginFile
	 * @param algorithm
	 * @return
	 * @throws IOException
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 */
	@ResponseBody
	@RequestMapping(method = RequestMethod.PUT, value = "/api/v2/inspectData", consumes = { "multipart/form-data" })
	public Object updateAlgorithm(
			@RequestPart(value = "pluginFile", required = false) MultipartFile pluginFile,
			@RequestHeader(value = "User-Key" , required = true) String userKey ,
			@RequestParam(value = "algorithm" , required = false) String algorithm 
			) throws IOException, IllegalAccessException, InvocationTargetException {

		log.debug("userkey = {}" , userKey) ; 
		
		// 文件上传检查
		File f = new File("/tmp/template.json");
		pluginFile.transferTo(f);

		String json = FileUtils.readFileToString(f);
		log.debug("json object = \r\n {}", json);

		FileUtils.forceDeleteOnExit(f);
		
		InspectionDataDto dto = JSONObject.parseObject(algorithm, InspectionDataDto.class) ;
		
		log.debug("algorithm = {}" , algorithm.toString()) ; 
		log.debug("dto = {}" , dto.toString()) ; 

		return "success";
	}

}
