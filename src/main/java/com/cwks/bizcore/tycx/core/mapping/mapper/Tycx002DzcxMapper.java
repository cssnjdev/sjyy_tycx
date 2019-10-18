package com.cwks.bizcore.tycx.core.mapping.mapper;

import com.cwks.bizcore.tycx.core.mapping.pojo.*;
import com.cwks.bizcore.yyfb.mapping.pojo.YyfwPjPojo;

import java.util.List;

public interface Tycx002DzcxMapper {

    public List select(Tycx002DzcxPojo pojo);

    public Tycx002DzcxPojo selectByPKey(Tycx002DzcxPojo pojo);

    public void updateByPKey(Tycx002DzcxPojo pojo);

    public void updateByPKeySelective(Tycx002DzcxPojo pojo);

    public void insert(Tycx002DzcxPojo pojo);

    public void insertSelective(Tycx002DzcxPojo pojo);

    public void deleteByPKey(Tycx002DzcxPojo pojo);
    
    public void insertPj(YyfwPjPojo pojo);
    
    public int searchPj(String pjdj);
    
    public List<YyfwPjPojo> selectPj(String pjdj);
    public int countPj(String fxyy_id);
    
    public String fxyymc(String fxyyid);
    
    public String fxyylxmc(String fxyylxdm);
    
    public String fxyylxdm(String fxyyid);

	public List<Tycx002WtfkPojo> searchWtfk(Tycx002WtfkPojo tycx002WtfkPojo);

	public List<Tycx002FjPojo> searchFj(Tycx002FjPojo tycx002FjPojo);

	public void insertWtfk(Tycx002WtfkPojo tycx002WtfkPojo);

	public void updateWtfk(Tycx002WtfkPojo tycx002WtfkPojo);

	public void insertFj(Tycx002FjPojo tycx002FjPojo);

	public void insertTS(Tycx002TuisongPojo tycx002TuisongPojo);
	public List<Tycx002TuisongPojo> queryTS(Tycx002TuisongPojo tycx002TuisongPojo);
	public void insertWtimg(Tycx002TuisongPojo tycx002TuisongPojo);

	public void insertFX(Tycx002FenxiangPojo tycx002FenxiangPojo);


}
