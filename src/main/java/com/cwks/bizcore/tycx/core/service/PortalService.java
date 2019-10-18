package com.cwks.bizcore.tycx.core.service;


import com.cwks.bizcore.tycx.core.utils.TreeBuilder;
import com.cwks.bizcore.tycx.core.utils.WfReqResUtil;
import com.cwks.bizcore.tycx.core.vo.GnsTree;
import com.cwks.common.api.dto.ext.RequestEvent;
import com.cwks.common.api.dto.ext.ResponseEvent;
import com.cwks.common.core.systemConfig.SystemContext;
import com.cwks.common.dao.JdbcDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * <p>File: PortalService.java</p>
 * <p>Title: 门户相关service</p>
 * <p>Description: 门户相关service</p>
 *
 * @author cssnj
 * @version 1.0
 * @history 修订历史（历次修订内容、修订人、修订时间等）
 */
@Component
@Service("portalService")
public class PortalService {

    private static Logger logger = LoggerFactory.getLogger(PortalService.class);

    @Autowired
    private JdbcDao jdbcDao;


    /**
     * @name queryGnsBySf
     * @Description 根据税务人员身份查询响应的管理功能树
     * @param requestEvent
     * @return ResponseEvent
     * @throws ParseException
     * @throws Exception
     * @history 修订历史（历次修订内容、修订人、修订时间等）
     */
    @SuppressWarnings("unchecked")
    public ResponseEvent queryGnsBySf(RequestEvent requestEvent) throws Exception{
        logger.info(this.getClass().getName()+"_queryGnsBySf");
        ResponseEvent resEvent = new ResponseEvent();
        HashMap<?, ?> reqMap = WfReqResUtil.transformReqMap(requestEvent);
        String cdlx = (String) reqMap.get("cdlx");
        String swrysf = (String) reqMap.get("swrysf");
        String treeS="";
        if(cdlx.equals("2")) {
            treeS=
                    "[{\n" +
                            "                    \"id\": -1,\n" +
                            "                    \"children\": [],\n" +
                            "                    \"spread\": false,\n" +
                            "                    \"title\": \"首页\",\n" +
                            "                    \"url\": \"\",\n" +
                            "                    \"icon\": \"&#xe68e;\"\n" +
                            "                },{\n" +
                            "                    id: \"1\",\n" +
                            "                    title: \"纳税服务\",\n" +
                            "                    icon: \"fa-cubes\",\n" +
                            "                    spread: false,\n" +
                            "                    children: [{\n" +
                            "                        id: \"11\",\n" +
                            "                        title: \"办税厅监控\",\n" +
                            "                        icon: \"&#xe6c6;\",\n" +
                            "                        url: \"\"\n" +
                            "                    }, {\n" +
                            "                        id: \"12\",\n" +
                            "                        title: \"信息提醒\",\n" +
                            "                        icon: \"&#xe63c;\",\n" +
                            "                        url: \"\"\n" +
                            "                    }]\n" +
                            "                }, {\n" +
                            "                    id: \"2\",\n" +
                            "                    title: \"基础管理\",\n" +
                            "                    icon: \"fa-stop-circle\",\n" +
                            "                    spread: false,\n" +
                            "                    children: [{\n" +
                            "                        id: \"21\",\n" +
                            "                        title: \"事项驱动\",\n" +
                            "                        icon: \"&#xe6c6;\",\n" +
                            "                        url: \"\"\n" +
                            "                    }]\n" +
                            "                }, {\n" +
                            "                    id: \"3\",\n" +
                            "                    title: \"风险管理\",\n" +
                            "                    icon: \"fa-stop-circle\",\n" +
                            "                    spread: false,\n" +
                            "                    children: [{\n" +
                            "                        id: \"31\",\n" +
                            "                        title: \"风险指标维护\",\n" +
                            "                        icon: \"&#xe6c6;\",\n" +
                            "                        url: \"\"\n" +
                            "                    }]\n" +
                            "                }, {\n" +
                            "                    id: \"4\",\n" +
                            "                    title: \"法制事务\",\n" +
                            "                    icon: \"fa-stop-circle\",\n" +
                            "                    spread: false,\n" +
                            "                    children: [{\n" +
                            "                        id: \"41\",\n" +
                            "                        title: \"税务法规\",\n" +
                            "                        icon: \"&#xe6c6;\",\n" +
                            "                        url: \"\"\n" +
                            "                    }]\n" +
                            "                }, {\n" +
                            "                    id: \"5\",\n" +
                            "                    title: \"查询统计\",\n" +
                            "                    icon: \"fa-stop-circle\",\n" +
                            "                    spread: false,\n" +
                            "                    children: [{\n" +
                            "                        id: \"51\",\n" +
                            "                        title: \"重点税源户分布\",\n" +
                            "                        icon: \"&#xe6c6;\",\n" +
                            "                        url: \"\"\n" +
                            "                    },{\n" +
                            "                        id: \"52\",\n" +
                            "                        title: \"一户式\",\n" +
                            "                        icon: \"&#xe6c6;\",\n" +
                            "                        url: \"\"\n" +
                            "                    }]\n" +
                            "                }]";
        }else {
            List<GnsTree> list= null;
            if(swrysf != null && "admin".equals(swrysf)){
                ArrayList<Object> param = new ArrayList<Object>();
                param.add(cdlx);
                list=jdbcDao.queryforlist(SystemContext.getSql("SQL_SYS_GET_TREE_XTGL_ALL"), param,GnsTree.class);
            }else{
                ArrayList<Object> param = new ArrayList<Object>();
                param.add(cdlx);
                param.add(swrysf);
                list=jdbcDao.queryforlist(SystemContext.getSql("SQL_SYS_GET_TREE_XTGL"), param,GnsTree.class);
            }
            if(list != null){
                List<TreeBuilder.Node> nodes = new ArrayList<TreeBuilder.Node>();
                TreeBuilder.Node node=null;
                for(GnsTree g:list) {
                    node= new TreeBuilder.Node();
                    node.setId(g.getId());
                    node.setPid(g.getPid());
                    node.setName(g.getName());
                    node.setUrl(g.getUrl());
                    node.setDisable(g.getDisable());
                    node.setIcon(g.getIcon());
                    node.setOpentype(g.getOpentype());
                    node.setSpread(g.getSpread().equals("1")?true:false);
                    nodes.add(node);
                }
                TreeBuilder treeBuilder = new TreeBuilder(nodes);
                treeS=treeBuilder.buildJSONTree();
            }
        }
        resEvent.getResMap().put("treeS", treeS);
        return resEvent;
    }

}
