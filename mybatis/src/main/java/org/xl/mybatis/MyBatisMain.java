package org.xl.mybatis;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * @author xulei
 */
public class MyBatisMain {

    public static void main(String[] args) throws IOException {
        String resourceLocation = "mybatis/config/mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resourceLocation);
        SqlSessionFactory sqlSessionFactory =
                new SqlSessionFactoryBuilder().build(inputStream);
        SqlSession sqlSession = sqlSessionFactory.openSession();
        try {
            PageHelper.startPage(1, 1);
            List<UserInfo> list = sqlSession.selectList("org.xl.mybatis.UserInfoMapper.getAll");
            PageInfo<UserInfo> pageInfo = new PageInfo<>(list);
            System.out.println(pageInfo);
        } finally {
            sqlSession.close();
        }
    }
}
