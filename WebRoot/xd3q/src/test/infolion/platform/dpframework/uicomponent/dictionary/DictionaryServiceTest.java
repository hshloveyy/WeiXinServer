/*
 * @(#)SysDictServiceTest.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：黄登辉
 *  时　间：2009-5-26
 *  描　述：创建
 */

package test.infolion.platform.dpframework.uicomponent.dictionary;

//import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import test.infolion.platform.core.service.JdbcServiceTest;

import com.infolion.platform.dpframework.uicomponent.dictionary.DictionaryRow;
import com.infolion.platform.dpframework.uicomponent.dictionary.domain.Dictionary;
import com.infolion.platform.dpframework.uicomponent.dictionary.service.DictionaryService;

public class DictionaryServiceTest extends JdbcServiceTest
{
	@Autowired
	private DictionaryService dictionaryService;

	public void setDictionaryService(DictionaryService dictionaryService)
	{
		this.dictionaryService = dictionaryService;
	}

	@Test
	public void testQueryAllDictionaryInfo()
	{
		List<Dictionary> dictList = dictionaryService.queryAllDictionaryInfo();
		showDictList(dictList);
	}

	@Test
	public void testQuerySignalDictionaryData()
	{
		Dictionary dictionary = new Dictionary();
		dictionary.setDictTable("dicttype");
		dictionary.setDictCodeColumn("TYPE_ID");
		dictionary.setDictNameColumn("TYPE_NAME");
		List dictList = dictionaryService
				.querySignalDictionaryData(dictionary);
		showDictDate(dictList);
	}

	@Test
	public void testQueryMutilDictionaryData()
	{
		Dictionary dictionary = new Dictionary();
		dictionary.setDictTable("dicttype");
		dictionary.setDictCodeColumn("TYPE_ID");
		dictionary.setDictNameColumn("TYPE_NAME");
		List dictList = dictionaryService
				.queryMutilDictionaryData(dictionary);
		showDictDate(dictList);
	}

	@Override
	protected List<String> customConfigLocatioins()
	{
		List<String> confs = new ArrayList<String>();
		confs.add("classpath:context/infolion-cache.xml");
		confs.add("classpath:testContext/infolion-core.xml");
		return confs;
	}

	protected void showDictList(List<Dictionary> dictList)
	{
		for (Dictionary dictionary : dictList)
		{
			System.out.println("dictionary.getDictName()=" + dictionary.getDictName());
			System.out.println("dictionary.getDictCnName()="
					+ dictionary.getDictCnName());
			System.out.println("dictionary.getDictCodeColumn()="
					+ dictionary.getDictCodeColumn());
			System.out.println("dictionary.getDictNameColumn()="
					+ dictionary.getDictNameColumn());
			System.out.println("dictionary.getDictTable()"
					+ dictionary.getDictTable());
		}
	}
	
	protected void showDictDate(List dictList)
	{
		for (Object object : dictList)
		{
			System.out.println(object);
		}
	}}
