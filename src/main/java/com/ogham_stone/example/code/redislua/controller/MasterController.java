package com.ogham_stone.example.code.redislua.controller;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ogham_stone.example.code.redislua.app.MasterService;
import com.ogham_stone.example.code.redislua.app.point.PointRequest;
import com.ogham_stone.example.code.redislua.app.point.lua.RouteIdMaxMinPosition;
import com.ogham_stone.example.code.redislua.app.tag.TagRequest;

@Controller
public class MasterController
{
	private final MasterService masterService;

	@Autowired
	public MasterController(MasterService masterService)
	{
		this.masterService = masterService;
	}

	@RequestMapping(value = "/findUniqueRouteIdsNear", method = RequestMethod.POST)
	public ResponseEntity<Set<RouteIdMaxMinPosition>> findUniqueRouteIdsNear(@RequestBody PointRequest request)
	{
		return new ResponseEntity<>(masterService.findUniqueRouteIdsNear(request.getNeLatitude(),
			request.getNeLongitude(), request.getSwLatitude(),
			request.getSwLongitude()), HttpStatus.OK);
	}

	@RequestMapping(value = "/findCommonTags", method = RequestMethod.POST)
	public ResponseEntity<Set<Integer>> findCommonTags(@RequestBody TagRequest request) throws JsonProcessingException
	{
		return new ResponseEntity<>(masterService.getCommonTags(request.getRouteIds()), HttpStatus.OK);
	}
}
