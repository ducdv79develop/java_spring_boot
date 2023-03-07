package com.local.ducdv.mapper;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import com.local.ducdv.entity.User;

@RunWith(SpringRunner.class)
@MybatisTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserMapperTest {
    @Autowired
    private UserMapper userMapper;

    @Test
    public void test_selectUser() {
        List<User> users = userMapper.selectUser();
        assertTrue(users.size() > 0);
    }

    @Test
    public void test_selectUserById() {
        User user = userMapper.selectUserById(11).orElse(null);
        assertNotNull(user);
        assertThat(user.getName()).isEqualTo("Van Duc 1");
        assertThat(user.getEmail()).isEqualTo("duc.dv1@beetechsoft.com");
        assertThat(user.getId()).isEqualTo(11);
    }

    @Test
    public void test_selectUserByIdXml() {
        User user = userMapper.selectUserByIdXml(11).orElse(null);
        assertNotNull(user);
        assertThat(user.getName()).isEqualTo("Van Duc 1");
        assertThat(user.getEmail()).isEqualTo("duc.dv1@beetechsoft.com");
        assertThat(user.getId()).isEqualTo(11);
    }

	@Test
	@Sql(scripts = "/create_user.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
	@Sql(scripts = "/delete_user.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
	public void test_insertUser() throws Exception {
		List<User> users =  userMapper.selectUser();
		boolean result = false;
		for(User user: users) {
			System.out.println(user.getEmail());
			
			if (user.getEmail().equals("testselectuser@usermapper.test")) {
				result = true;
				break;
			}
		}
		System.out.println(result);
		assertTrue(result);
	}
}
