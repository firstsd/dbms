package Database;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import entity.*;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class DBSingleton extends Database {

    private final String url;

    DBSingleton(String url) {
        this.url = url;
    }

    @Override
    void connect() throws Exception {
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        conn = DriverManager.getConnection(url);
    }

    private void checkConn() throws Exception {
        if (conn == null || conn.isClosed()) {
            connect();
        }
    }

    public List<Customer> getCustomers() {
        List<Customer> ret = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        final String sql = "select * from customer";
        List<Object> params = new ArrayList<Object>();
        try {
            checkConn();
            ps = preparedStatement(sql, params.toArray());
            rs = ps.executeQuery();
            while (rs.next()) {
                if (ret == null) {
                    ret = new ArrayList();
                }
                Customer customer = new Customer(rs.getInt("custID"), rs.getString("firstName"), rs.getString("lastName"), rs.getString("phoneNo"), getService(rs.getString("serviceID")), rs.getString("address"), getCountry(rs.getString("countryCode")));
                ret.add(customer);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(ps);
            close(rs);
        }
        return ret;
    }

    public Customer getCustomer(String phonNo) {
        Customer ret = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        final String sql = "SELECT firstName, lastName, address, phoneNo FROM customer WHERE phoneNo=" + phonNo;
        List<Object> params = new ArrayList<Object>();
        try {
            checkConn();
            ps = preparedStatement(sql, params.toArray());
            rs = ps.executeQuery();
            while (rs.next()) {
                Customer customer = new Customer(rs.getInt("custID"), rs.getString("firstName"), rs.getString("lastName"), rs.getString("phoneNo"), getService(rs.getString("serviceID")), rs.getString("address"), getCountry(rs.getString("countryCode")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(ps);
            close(rs);
        }
        return ret;
    }

    public Integer amountDue(String phoneNo, String startDate,  String endDate){
        Integer ret = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        final String sql = "SELECT\n" +
                "\tsum(\n" +
                "\t\tCASE\n" +
                "\t\tWHEN calltime BETWEEN pt.peakPeriodStart\n" +
                "\t\tAND pt.offPeakPeriodStart THEN\n" +
                "\t\t\tr.peak * duration / 60\n" +
                "\t\tELSE\n" +
                "\t\t\tr.offPeak * duration / 60\n" +
                "\t\tEND\n" +
                "\t) AS amountDue\n" +
                "FROM\n" +
                "\tcalls ca, customer cu, callingCode co, service s, peakTime pt,\n" +
                "(   select * from rate t1  where t1.changeDate in (\n" +
                "select MAX(changeDate) from rate t2 where t1.destCode =t2.destCode and t1.fromcode=t2.fromcode and t1.serviceId=t2.serviceId)) r\n" +
                "WHERE\n" +
                "\tca.fromPhoneNo = cu.phoneNo AND ca.toCode = co.countryCode AND s.serviceID = cu.serviceID AND cu.phoneNo = "+phoneNo+"\n" +
                "AND ca.fromCode = pt.countryCode AND cu.serviceID = pt.serviceID AND r.serviceID = cu.serviceID AND r.fromCode = ca.fromCode\n" +
                "AND ca.toCode = r.destCode AND ca.callDate BETWEEN "+startDate+" AND "+endDate+" ORDER BY 1";
        List<Object> params = new ArrayList<Object>();
        try {
            checkConn();
            ps = preparedStatement(sql, params.toArray());
            rs = ps.executeQuery();
            while (rs.next()) {
                ret =  rs.getInt("amountDue");//new Customer(rs.getInt("custID"), rs.getString("firstName"), rs.getString("lastName"), rs.getString("phoneNo"), getService(rs.getString("serviceID")), rs.getString("address"), getCountry(rs.getString("countryCode")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(ps);
            close(rs);
        }
        return ret;
    }

    public List<Staff> getStaffs() {
        List<Staff> ret = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        final String sql = "select * from staff";
        List<Object> params = new ArrayList<Object>();
        try {
            checkConn();
            ps = preparedStatement(sql, params.toArray());
            rs = ps.executeQuery();
            while (rs.next()) {
                if (ret == null) {
                    ret = new ArrayList();
                }
                Staff staff = new Staff(rs.getInt("staffID"), rs.getString("staffName"));
                ret.add(staff);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(ps);
            close(rs);
        }
        return ret;
    }

    public List<CallingCode> getCountries() {
        List<CallingCode> ret = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        final String sql = "select * from callingCode";
        List<Object> params = new ArrayList<Object>();
        try {
            checkConn();
            ps = preparedStatement(sql, params.toArray());
            rs = ps.executeQuery();
            while (rs.next()) {
                if (ret == null) {
                    ret = new ArrayList();
                }
                CallingCode country = new CallingCode(rs.getInt("countryCode"), rs.getString("countryName"));
                ret.add(country);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(ps);
            close(rs);
        }
        return ret;
    }

    public List<Service> getServices() {
        List<Service> ret = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        final String sql = "select * from service";
        List<Object> params = new ArrayList<Object>();
        try {
            checkConn();
            ps = preparedStatement(sql, params.toArray());
            rs = ps.executeQuery();
            while (rs.next()) {
                if (ret == null) {
                    ret = new ArrayList();
                }
                Service service = new Service(rs.getInt("serviceID"), rs.getString("serviceName"));
                ret.add(service);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(ps);
            close(rs);
        }
        return ret;
    }

    public CallingCode getCountry(String callingCode) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        final String sql = "select * from callingCode where countryCode = " + callingCode;
        List<Object> params = new ArrayList<Object>();
        try {
            checkConn();
            ps = preparedStatement(sql, params.toArray());
            rs = ps.executeQuery();
            while (rs.next()) {
                CallingCode code = new CallingCode(rs.getInt("countryCode"), rs.getString("countryName"));
                return code;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(ps);
            close(rs);
        }
        return null;
    }

    public Service getService(String serviceID) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        final String sql = "select * from service where serviceID = " + serviceID;
        List<Object> params = new ArrayList<Object>();
        try {
            checkConn();
            ps = preparedStatement(sql, params.toArray());
            rs = ps.executeQuery();
            while (rs.next()) {
                Service service = new Service(rs.getInt("serviceID"), rs.getString("serviceName"));
                return service;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(ps);
            close(rs);
        }
        return null;
    }

    public List<RateTmp> getRates() {
        List<RateTmp> ret = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        final String sql = "SELECT rateID, s.serviceName, f.countryName AS fromCountry, d.countryName AS toCountry, peak, offPeak, changeDate FROM rate r, service s, callingCode f, callingCode d WHERE r.serviceID = s.serviceID and r.fromCode = f.countryCode and r.destCode = d.countryCode";
        List<Object> params = new ArrayList<Object>();
        try {
            checkConn();
            ps = preparedStatement(sql, params.toArray());
            rs = ps.executeQuery();
            while (rs.next()) {
                if (ret == null) {
                    ret = new ArrayList<RateTmp>();
                }
                RateTmp rateTmp = new RateTmp(rs.getInt("rateID"), rs.getString("serviceName"), rs.getString("fromCountry"), rs.getString("toCountry"), rs.getDouble("peak"), rs.getDouble("offPeak"), rs.getDate("changeDate"));
                ret.add(rateTmp);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(rs);
            close(ps);
        }
        return ret;
    }

    public List<ProcessCall> getCalls() {
        List<ProcessCall> ret = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        final String sql = "SELECT callID, co.countryName as fromCountry, co2.countryName as toCountry, fromPhoneNo, toPhoneNo, duration, callDate, callTime FROM calls ca, callingCode co, callingCode co2 WHERE ca.fromCode = co.countryCode and ca.toCode = co2.countryCode";
        List<Object> params = new ArrayList<Object>();
        try {
            checkConn();
            ps = preparedStatement(sql, params.toArray());
            rs = ps.executeQuery();
            while (rs.next()) {
                if (ret == null) {
                    ret = new ArrayList<ProcessCall>();
                }
                ProcessCall processCall = new ProcessCall(rs.getInt("callID"), rs.getString("fromCountry"), rs.getString("toCountry"), rs.getString("fromPhoneNo"), rs.getString("toPhoneNo"), rs.getInt("duration"), rs.getDate("callDate"), rs.getTime("callTime"));
                ret.add(processCall);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(rs);
            close(ps);
        }
        return ret;
    }

    public String exportTrafficSummary(String startDate, String endDate) {
        String ret = null;
        PreparedStatement ps = null;
        final String sql = "EXEC dbo.generateTrafficSummary '" + startDate + "', '" + endDate + "';";
        try {
            checkConn();
            ps = preparedStatement(sql);
            if (ps.executeUpdate() > 0) {
                ret = "success";
            }
        } catch (Exception e) {
            ret = e.getMessage();
        } finally {
            close(ps);
        }
        return ret;
    }

    public String exportRate(Integer serviceID, Integer fromCountryID) {
        String ret = null;
        PreparedStatement ps = null;
        final String sql = "EXEC dbo.exportCurrentRate " + serviceID + ", " + fromCountryID;
        try {
            checkConn();
            ps = preparedStatement(sql);
            if (ps.executeUpdate() > 0) {
                ret = "success";
            }
        } catch (Exception e) {
            ret = e.getMessage();
        } finally {
            close(ps);
        }
        return ret;
    }

    public String createBill(String phone, String startDate, String endDate) {
        String ret = null;
        PreparedStatement ps = null;
        final String sql = "EXEC dbo.generateCustomerBill"+phone+", "+startDate+", "+endDate;
        try {
            checkConn();
            ps = preparedStatement(sql);
            if (ps.executeUpdate() > 0) {
                ret = "success";
            }
        } catch (Exception e) {
            ret = e.getMessage();
        } finally {
            close(ps);
        }
        return ret;
    }

    public String updateRate(String filename, String tablename, String sheetName) {
        String ret = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        final String sql = "exec dbo.importExcel_to_SQL " + sheetName + "$, '" + filename + "', " + tablename + ";";
        try {
            checkConn();
            ret = sql;
            ps = preparedStatement(sql);
            int a = ps.executeUpdate();
                ret += String.valueOf(a);
        } catch (Exception e) {
            ret = e.getMessage();
        } finally {
            close(ps);
        }
        return ret;
    }


    public String addCustomer(Customer customer) {
        String ret = null;
        PreparedStatement ps = null;
        final String sql = "EXEC dbo.addCustomer " + customer.getfName() + ", " +
                customer.getlName() + ", " + customer.getPhoneNo() + ", " +
                customer.getService().getServiceId() + ", '" + customer.getAddress() + "', " +
                customer.getCountryCode().getCountryCode() + ", " + customer.getCustID() + ", " + 8;
        try {
            checkConn();
            ps = preparedStatement(sql);
            if (ps.executeUpdate() > 0) {
                ret = "success";
            }
        } catch (Exception e) {
            ret = e.getMessage();
        } finally {
            close(ps);
        }
        return ret;
    }

//	public List<Course> getCourseList() {
//		List<Course> ret = null;
//		PreparedStatement ps = null;
//		ResultSet rs = null;
//		final String sql = "SELECT *FROM course";
//		List<Object> params = new ArrayList<>();
//		try {
//			checkConn();
//			ps = preparedStatement(sql, params.toArray());
//			rs = ps.executeQuery();
//			while (rs.next()) {
//				if (ret == null) {
//					ret = new ArrayList<>();
//				}
//				Course onecourse = new Course(rs.getInt("ID"), rs.getString("courseName"), rs.getString("courseCode"));
//				ret.add(onecourse);
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		} finally {
//			close(ps);
//			close(rs);
//		}
//		return ret;
//	}
//
//	public String insertCourse(Course course) {
//		String ret = null;
//		PreparedStatement ps = null;
//		final String sql = "INSERT INTO course(courseName, courseCode) VALUES (?,?)";
//		try {
//			checkConn();
//			ps = preparedStatement(sql, course.getCourseName(), course.getCourseCode());
//			if (ps.executeUpdate() > 0)
//				ret = "Success";
//		} catch (Exception e) {
//			e.printStackTrace();
//			ret = e.getMessage();
//		} finally {
//			close(ps);
//		}
//
//		return ret;
//	}
//
//	public Course getStudentCourse(String courseCode) {
//		PreparedStatement ps = null;
//		ResultSet rs = null;
//		final String sql = "SELECT * FROM course where course.ID = " + courseCode;
//		List<Object> params = new ArrayList<>();
//
//		try {
//			checkConn();
//			ps = preparedStatement(sql, params.toArray());
//			rs = ps.executeQuery();
//			while (rs.next()) {
//				Course c = new Course(rs.getInt("ID"), rs.getString("courseName"), rs.getString("courseCode"));
//				return c;
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		} finally {
//			close(ps);
//			close(rs);
//		}
//		return null;
//	}
//
//	public List<Student> getStudent() {
//		List<Student> ret = null;
//		PreparedStatement ps = null;
//		ResultSet rs = null;
//		final String sql = "SELECT s.studentNumber, u.username, u.password, u.firstname, u.lastname, s.course_ID FROM user u\r\n"
//				+ "INNER JOIN student s ON u.ID = s.user_ID";
//		List<Object> params = new ArrayList<>();
//		try {
//			checkConn();
//			ps = preparedStatement(sql, params.toArray());
//			rs = ps.executeQuery();
//			while (rs.next()) {
//				if (ret == null) {
//					ret = new ArrayList<>();
//				}
//				Student student = new Student(rs.getString("studentNumber"), rs.getString("username"),
//						rs.getString("password"), rs.getString("firstname"), rs.getString("lastname"));
//				if (!rs.getString("course_ID").equals("")) {
//					student.setCourse(getStudentCourse(rs.getString("course_ID")));
//				}
//				ret.add(student);
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		} finally {
//			close(ps);
//			close(rs);
//		}
//		return ret;
//	}
//
//	public List<TestGrade> getTestGrade() {
//		List<TestGrade> ret = null;
//		PreparedStatement ps = null;
//		ResultSet rs = null;
//		final String sql = "SELECT * FROM testgrade";
//		List<Object> params = new ArrayList<>();
//		try {
//			checkConn();
//			ps = preparedStatement(sql, params.toArray());
//			rs = ps.executeQuery();
//			while (rs.next()) {
//				if (ret == null) {
//					ret = new ArrayList<>();
//				}
//				Test te = null;
//				for (Test t : getTestList()) {
//					if (t.getId() == rs.getInt("test_ID")) {
//						te = t;
//					}
//				}
//				Student stu = null;
//				for (Student st : getStudent()) {
//					if (st.getStudentCode().equals(rs.getString("student_user_ID"))) {
//						stu = st;
//					}
//				}
//				TestGrade testGrade = new TestGrade(rs.getInt("ID"), rs.getString("test_ID"), te, stu);
//				testGrade.setMidResult(rs.getInt("midResult"));
//				testGrade.setFinalResult(rs.getInt("finalResult"));
//				ret.add(testGrade);
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		} finally {
//			close(ps);
//			close(rs);
//		}
//		return ret;
//	}
//
//	public String insertTestGrade(TestGrade testGrade) {
//		String ret = null;
//		PreparedStatement ps = null;
//		final String sql = "INSERT INTO testgrade(student_user_ID, test_ID, midResult, finalResult) VALUES (?,?,?,?)";
//		try {
//			checkConn();
//			ps = preparedStatement(sql, testGrade.getStudent().getStudentCode(), testGrade.getTest().getId(),
//					testGrade.getMidResult(), testGrade.getFinalResult());
//			if (ps.executeUpdate() > 0)
//				ret = "Success";
//		} catch (Exception e) {
//			e.printStackTrace();
//			ret = e.getMessage();
//		} finally {
//			close(ps);
//		}
//		return ret;
//	}
//
//	public String updateTestGradeMid(double midResult, String studentUserId) {
//		String ret = null;
//		PreparedStatement ps = null;
//		final String sql = "UPDATE testgrade SET midResult=? WHERE student_user_ID=?";
//		try {
//			checkConn();
//			ps = preparedStatement(sql, midResult, studentUserId);
//			if (ps.executeUpdate() > 0) {
//				ret = "Success";
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//			ret = e.getMessage();
//		} finally {
//			close(ps);
//		}
//		return ret;
//	}
//
//	public String updateTestGradeFinal(double finalResult, String studentUserId) {
//		String ret = null;
//		PreparedStatement ps = null;
//		final String sql = "UPDATE testgrade SET finalResult=? WHERE student_user_ID=?";
//		try {
//			checkConn();
//			ps = preparedStatement(sql, finalResult, studentUserId);
//			if (ps.executeUpdate() > 0) {
//				ret = "Success";
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//			ret = e.getMessage();
//		} finally {
//			close(ps);
//		}
//		return ret;
//	}
//
//	// Students for learning specific lesson
//	// public List<Student> getStudentsforCourse(Course course) {
//	// List<Student> ret = null;
//	// PreparedStatement ps = null;
//	// ResultSet rs = null;
//	//
//	// for(Course co : getCourseList()) {
//	// if(co.getCourseCode().equals(course.getCourseCode())) {
//	//
//	// }
//	// }
//	//
//	// final String sql = "SELECT * FROM testgrade";
//	// List<Object> params = new ArrayList<>();
//	// try {
//	// checkConn();
//	// ps = preparedStatement(sql, params.toArray());
//	// rs = ps.executeQuery();
//	// while (rs.next()) {
//	// if (ret == null) {
//	// ret = new ArrayList<>();
//	// }
//	// TestGrade testgrade = new TestGrade(rs.getInt("ID"),
//	// rs.getString("student_user_ID"), rs.getInt("test_ID"));
//	// testgrade.setTestResult(rs.getDouble("testResult"));
//	// if(rs.getString("coursegrade_ID") != null &&
//	// !rs.getString("coursegrade_ID").equals("")) {
//	// student.setCourse(getStudentCourse(rs.getString("coursegrade_ID")));
//	// }
//	// ret.add(student);
//	// }
//	// } catch (Exception e) {
//	// e.printStackTrace();
//	// } finally {
//	// close(ps);
//	// close(rs);
//	// }
//	// return ret;
//	// }
//
//	public String deleteCourse(Course course) {
//		String ret = null;
//		PreparedStatement ps = null;
//		final String sql = "DELETE FROM course WHERE ID=?";
//		try {
//			checkConn();
//			ps = preparedStatement(sql, course.getCourseId());
//			if (ps.executeUpdate() > 0) {
//				ret = "Success";
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//			ret = e.getMessage();
//		} finally {
//			close(ps);
//		}
//		return ret;
//	}
//
//	public String updateCourse(Course course) {
//		String ret = null;
//		PreparedStatement ps = null;
//		final String sql = "UPDATE course SET courseName=?, courseCode=? WHERE ID=?";
//		try {
//			checkConn();
//			ps = preparedStatement(sql, course.getCourseName(), course.getCourseCode(), course.getCourseId());
//			if (ps.executeUpdate() > 0) {
//				ret = "Success";
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//			ret = e.getMessage();
//		} finally {
//			close(ps);
//		}
//		return ret;
//	}
//
//	public User getUser(String username, String password, String usertype) {
//		User ret = null;
//		PreparedStatement ps = null;
//		ResultSet rs = null;
//		final String sql_student = "SELECT * FROM `user` as u JOIN student as s on s.user_ID=u.ID WHERE username=? and `password`=?";
//		final String sql_faculty = "SELECT * FROM `user` as u JOIN faculty as s on s.user_ID=u.ID WHERE username=? and `password`=?";
//		final String sql_admin = "SELECT * FROM `user` as u JOIN admin as s on s.user_ID=u.ID WHERE username=? and `password`=?";
//		try {
//			checkConn();
//			if (usertype.equals("Student")) {
//				ps = preparedStatement(sql_student, username, password);
//				rs = ps.executeQuery();
//				if (rs.next()) {
//					if (ret == null) {
//
//						Student st = new Student(rs.getString("studentNumber"), rs.getString("username"),
//								rs.getString("password"), rs.getString("firstname"), rs.getString("lastname"));
//
//						if (rs.getString("course_ID") != null && !rs.getString("course_ID").equals("")) {
//							st.setCourse(getStudentCourse(rs.getString("course_ID")));
//						}
//
//						ret = st;
//
//					}
//				} else {
//					Alert alert = new Alert(AlertType.WARNING);
//					alert.setTitle("WARNING");
//					alert.setHeaderText("STUDENT NOT FOUND");
//					alert.setContentText("Ooops, input values are invalid!");
//					alert.showAndWait();
//				}
//			} else if (usertype.equals("Faculty")) {
//				ps = preparedStatement(sql_faculty, username, password);
//				rs = ps.executeQuery();
//				if (rs.next()) {
//					if (ret == null) {
//						ret = new Faculty(rs.getInt("ID"), rs.getString("facultyNumber"), rs.getString("degree"),
//								rs.getString("username"), rs.getString("password"), rs.getString("firstname"),
//								rs.getString("lastname"));
//					}
//				} else {
//					Alert alert = new Alert(AlertType.WARNING);
//					alert.setTitle("WARNING");
//					alert.setHeaderText("FACULTY NOT FOUND");
//					alert.setContentText("Ooops, input values are invalid!");
//					alert.showAndWait();
//				}
//			} else if (usertype.equals("Admin")) {
//				ps = preparedStatement(sql_admin, username, password);
//				rs = ps.executeQuery();
//				if (rs.next()) {
//					if (ret == null) {
//						ret = new Admin(rs.getString("adminNumber"), rs.getString("username"), rs.getString("password"),
//								rs.getString("firstname"), rs.getString("lastname"));
//					}
//				} else {
//					Alert alert = new Alert(AlertType.WARNING);
//					alert.setTitle("WARNING");
//					alert.setHeaderText("ADMIN NOT FOUND");
//					alert.setContentText("Ooops, input values are invalid!");
//					alert.showAndWait();
//				}
//			}
//
//		} catch (Exception e) {
//			e.printStackTrace();
//		} finally {
//			close(rs);
//			close(ps);
//		}
//		return ret;
//	}
//
//	public Test getTest(Course course, String testType) {
//		// Test test;
//		PreparedStatement ps = null;
//		ResultSet rs = null;
//		final String sql = "SELECT * FROM test where test.courseCode ='" + course.getCourseCode()
//				+ "' and test.testType ='" + testType + "';";
//		List<Object> params = new ArrayList<>();
//		try {
//			checkConn();
//			ps = preparedStatement(sql, params.toArray());
//			rs = ps.executeQuery();
//			if (rs.next()) {
//				Test t = new Test(rs.getInt("ID"), rs.getString("courseCode"), rs.getString("testType"), course,
//						rs.getInt("isEnable"), rs.getInt("durationTime"));
//				return t;
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		} finally {
//			close(ps);
//			close(rs);
//		}
//		return null;
//	}
//
//	public List<Question> getQuestionList(Test test) {
//		List<Question> questionList = new ArrayList<>();
//		PreparedStatement ps = null;
//		ResultSet rs = null;
//		final String sql = "SELECT * FROM question where question.test_ID =" + test.getId();
//		List<Object> params = new ArrayList<>();
//		try {
//			checkConn();
//			ps = preparedStatement(sql, params.toArray());
//			rs = ps.executeQuery();
//			while (rs.next()) {
//				Question q = new Question(rs.getInt("ID"), rs.getString("question"), rs.getInt("point"), test);
//				questionList.add(q);
//			}
//			return questionList;
//		} catch (Exception e) {
//			e.printStackTrace();
//		} finally {
//			close(ps);
//			close(rs);
//		}
//		return null;
//	}
//
//	public List<Answer> getAnswerList(Question question) {
//		List<Answer> answerList = new ArrayList<>();
//		PreparedStatement ps = null;
//		ResultSet rs = null;
//		final String sql = "SELECT * FROM answer where answer.question_ID =" + question.getId();
//		List<Object> params = new ArrayList<>();
//		try {
//			checkConn();
//			ps = preparedStatement(sql, params.toArray());
//			rs = ps.executeQuery();
//			while (rs.next()) {
//				Answer a = new Answer(rs.getInt("ID"), rs.getString("answer"), rs.getInt("correctAnswer"), question);
//				answerList.add(a);
//			}
//			return answerList;
//		} catch (Exception e) {
//			e.printStackTrace();
//		} finally {
//			close(ps);
//			close(rs);
//		}
//		return null;
//	}
//
//	// ======================================================================================
//
//	public Integer insertFaculty(Faculty fac) {
//		PreparedStatement ps = null, ps1 = null;
//		ResultSet rs = null;
//		int last_inserted_id = 0;
//		final String sql_user = "INSERT INTO `user`(username,`password`,firstname,lastname) VALUES(?,?,?,?)";
//		final String sql_faculty = "INSERT INTO faculty(facultyNumber,degree,user_ID) VALUES(?,?,?)";
//		try {
//			checkConn();
//			ps = preparedStatement(Statement.RETURN_GENERATED_KEYS, sql_user, fac.getUserName(), fac.getPassword(),
//					fac.getFirstName(), fac.getLastName());
//
//			if (ps.executeUpdate() > 0) {
//				rs = ps.getGeneratedKeys();
//				if (rs.next()) {
//					last_inserted_id = rs.getInt(1);
//				}
//				ps1 = preparedStatement(sql_faculty, fac.getFacultyCode(), fac.getFacultyDegree(), last_inserted_id);
//				ps1.executeUpdate();
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		} finally {
//			close(ps);
//			close(ps1);
//			close(rs);
//		}
//		return last_inserted_id;
//	}
//
//	public List<Faculty> getFaculty() {
//		List<Faculty> ret = null;
//		PreparedStatement ps = null, ps1 = null;
//		ResultSet rs = null, rs1 = null;
//		final String sql = "SELECT * FROM `faculty` as f LEFT JOIN `user` as u on f.user_ID = u.ID";
//		final String sql2 = "SELECT * FROM course WHERE ID=?";
//		List<Object> params = new ArrayList<>();
//		try {
//			checkConn();
//			ps = preparedStatement(sql, params.toArray());
//			rs = ps.executeQuery();
//			while (rs.next()) {
//				if (ret == null) {
//					ret = new ArrayList<>();
//				}
//				Faculty onefac = new Faculty(rs.getInt("user_ID"), rs.getString("facultyNumber"),
//						rs.getString("degree"), rs.getString("username"), rs.getString("password"),
//						rs.getString("firstname"), rs.getString("lastname"));
//				ps1 = preparedStatement(sql2, rs.getInt("course_ID"));
//				rs1 = ps1.executeQuery();
//				if (rs1.next()) {
//					Course c = new Course(rs1.getInt("ID"), rs1.getString("courseName"), rs1.getString("courseCode"));
//					onefac.setCourse(c);
//				}
//				ret.add(onefac);
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		} finally {
//			close(ps);
//			close(rs);
//			close(ps1);
//			close(rs1);
//		}
//		return ret;
//	}
//
//	public String updateFaculty(Faculty faculty, Course cour) {
//		String ret = null;
//		PreparedStatement ps = null;
//		final String sql = "UPDATE faculty SET course_ID=? WHERE user_ID=?";
//		try {
//			checkConn();
//			ps = preparedStatement(sql, cour.getCourseId(), faculty.getFacultyId());
//			if (ps.executeUpdate() > 0) {
//				ret = "Success";
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//			ret = e.getMessage();
//		} finally {
//			close(ps);
//		}
//		return ret;
//	}
//
//	public String updateStudent(Student student, Course cour) {
//		String ret = null;
//		PreparedStatement ps = null;
//		final String sql = "UPDATE student SET course_ID=? WHERE user_ID=?";
//		try {
//			checkConn();
//			ps = preparedStatement(sql, cour.getCourseId(), student.getStudentId());
//			if (ps.executeUpdate() > 0) {
//				ret = "Success";
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//			ret = e.getMessage();
//		} finally {
//			close(ps);
//		}
//		return ret;
//	}
//
//	public String deleteFaculty(Faculty fac) {
//		String ret = null;
//		PreparedStatement ps = null, ps1 = null;
//		final String sql_faculty = "DELETE FROM faculty where user_ID=?";
//		final String sql_user = "DELETE FROM `user` WHERE ID=?";
//		try {
//			checkConn();
//			ps = preparedStatement(sql_faculty, fac.getFacultyId());
//			if (ps.executeUpdate() > 0) {
//				ret = "Success";
//			}
//			if (ret.equals("Success")) {
//				ps1 = preparedStatement(sql_user, fac.getFacultyId());
//				if (ps1.executeUpdate() > 0) {
//					ret = "Success";
//				}
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//			ret = e.getMessage();
//		} finally {
//			close(ps);
//			close(ps1);
//		}
//		return ret;
//	}
//
//	public String editFaculty(Faculty faculty, String column, String value) {
//		String ret = null;
//		PreparedStatement ps = null;
//		String sql;
//		if (column.equals("facultyNumber") || column.equals("degree"))
//			sql = "UPDATE faculty SET " + column + "=? WHERE user_ID=?";
//		else
//			sql = "UPDATE `user` SET " + column + "=? WHERE ID=?";
//
//		try {
//			checkConn();
//			ps = preparedStatement(sql, value, faculty.getFacultyId());
//			if (ps.executeUpdate() > 0) {
//				ret = "Success";
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//			ret = e.getMessage();
//		} finally {
//			close(ps);
//		}
//		return ret;
//	}
//
//	public List<CourseGrade> getCourseGrade(Course course) {
//		List<CourseGrade> ret = null;
//		PreparedStatement ps = null;
//		ResultSet rs = null;
//		final String sql = "SELECT * FROM coursegrade";
//		List<Object> params = new ArrayList<>();
//		try {
//			checkConn();
//			ps = preparedStatement(sql, params.toArray());
//			rs = ps.executeQuery();
//			while (rs.next()) {
//				if (ret == null) {
//					ret = new ArrayList<>();
//				}
//				CourseGrade courseGrade = new CourseGrade(rs.getInt("ID"), rs.getString("courseGradeType"),
//						rs.getDouble("attendance"), rs.getDouble("homework"), course, rs.getDouble("totalgrade"));
//				for (Student st : getStudent()) {
//					if (st.getStudentCode().equals(rs.getString("studentNumber"))) {
//						courseGrade.setStudent(st);
//					}
//				}
//				for (TestGrade t : getTestGrade()) {
//					if (t.getStudent().getStudentCode().equals(rs.getString("studentNumber"))) {
//						courseGrade.setTestGrade(t);
//					}
//				}
//				ret.add(courseGrade);
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		} finally {
//			close(ps);
//			close(rs);
//		}
//		return ret;
//	}
//
//	public String insertCourseGrade(Student st, Course co, TestGrade tGrade, String courseGradreType, Double attendance,
//			Double homeWork) {
//		String ret = null;
//		PreparedStatement ps = null;
//		final String sql_user = "INSERT INTO `coursegrade`(courseGradeType,testGrade,attendance,homeWork,courseCode,course_ID,testGrade_ID,studentNumber) VALUES(?,?,?,?,?,?,?,?)";
//		try {
//			checkConn();
//			ps = preparedStatement(sql_user, courseGradreType, tGrade.getId(), attendance, homeWork, co.getCourseCode(),
//					co.getCourseId(), tGrade.getId(), st.getStudentCode());
//			if (ps.executeUpdate() > 0) {
//				ret = "success";
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		} finally {
//			close(ps);
//		}
//		return ret;
//	}
//
//	public String updateCourseGradeHomeWork(double homeWork, String studentID) {
//		String ret = null;
//		PreparedStatement ps = null;
//		final String sql = "UPDATE coursegrade SET homework=? WHERE studentNumber=?";
//		try {
//			checkConn();
//			ps = preparedStatement(sql, homeWork, studentID);
//			if (ps.executeUpdate() > 0) {
//				ret = "Success";
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//			ret = e.getMessage();
//		} finally {
//			close(ps);
//		}
//		return ret;
//	}
//
//	public String updateCourseTotalGrade(String studentID, Double total) {
//		String ret = null;
//		PreparedStatement ps = null;
//		final String sql = "UPDATE coursegrade SET totalgrade=? WHERE studentNumber=?";
//		try {
//			checkConn();
//			System.out.println(total);
//			ps = preparedStatement(sql, total, studentID);
//			System.out.println(ps);
//			if (ps.executeUpdate() > 0) {
//				ret = "Success";
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//			ret = e.getMessage();
//		} finally {
//			close(ps);
//		}
//		return ret;
//	}
//
//	public String updateCourseGradeAttendance(double attendance, String studentID) {
//		String ret = null;
//		PreparedStatement ps = null;
//		final String sql = "UPDATE coursegrade SET homework=? WHERE studentNumber=?";
//		try {
//			checkConn();
//			ps = preparedStatement(sql, attendance, studentID);
//			if (ps.executeUpdate() > 0) {
//				ret = "Success";
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//			ret = e.getMessage();
//		} finally {
//			close(ps);
//		}
//		return ret;
//	}
//
//	public Integer insertStudent(Student stu) {
//		PreparedStatement ps = null, ps1 = null;
//		ResultSet rs = null;
//		int last_inserted_id = 0;
//		final String sql_user = "INSERT INTO `user`(username,`password`,firstname,lastname) VALUES(?,?,?,?)";
//		final String sql_student = "INSERT INTO student(studentNumber,user_ID) VALUES(?,?)";
//		try {
//			checkConn();
//			ps = preparedStatement(Statement.RETURN_GENERATED_KEYS, sql_user, stu.getUserName(), stu.getPassword(),
//					stu.getFirstName(), stu.getLastName());
//
//			if (ps.executeUpdate() > 0) {
//				rs = ps.getGeneratedKeys();
//				if (rs.next()) {
//					last_inserted_id = rs.getInt(1);
//				}
//				ps1 = preparedStatement(sql_student, stu.getStudentCode(), last_inserted_id);
//				ps1.executeUpdate();
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		} finally {
//			close(ps);
//			close(ps1);
//			close(rs);
//		}
//		return last_inserted_id;
//	}
//
//	public List<Student> getStudents() {
//		List<Student> ret = null;
//		PreparedStatement ps = null, ps1 = null;
//		ResultSet rs = null, rs1 = null;
//		final String sql = "SELECT * FROM `student` as s LEFT JOIN `user` as u on s.user_ID = u.ID";
//		final String sql2 = "SELECT * FROM course WHERE ID=?";
//		List<Object> params = new ArrayList<>();
//		try {
//			checkConn();
//			ps = preparedStatement(sql, params.toArray());
//			rs = ps.executeQuery();
//			while (rs.next()) {
//				if (ret == null) {
//					ret = new ArrayList<>();
//				}
//				Student onestu = new Student(rs.getInt("user_ID"), rs.getString("studentNumber"),
//						rs.getString("username"), rs.getString("password"), rs.getString("firstname"),
//						rs.getString("lastname"));
//
//				ps1 = preparedStatement(sql2, rs.getInt("course_ID"));
//				rs1 = ps1.executeQuery();
//				if (rs1.next()) {
//					Course c = new Course(rs1.getInt("ID"), rs1.getString("courseName"), rs1.getString("courseCode"));
//					onestu.setCourse(c);
//				}
//				ret.add(onestu);
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		} finally {
//			close(ps);
//			close(rs);
//			close(ps1);
//			close(rs1);
//		}
//		return ret;
//	}
//
//	public String deleteStudent(Student stu) {
//		String ret = null;
//		PreparedStatement ps = null, ps1 = null;
//		final String sql_student = "DELETE FROM student where user_ID=?";
//		final String sql_user = "DELETE FROM `user` WHERE ID=?";
//		try {
//			checkConn();
//			ps = preparedStatement(sql_student, stu.getStudentId());
//			if (ps.executeUpdate() > 0) {
//				ret = "Success";
//			}
//			if (ret.equals("Success")) {
//				ps1 = preparedStatement(sql_user, stu.getStudentId());
//				if (ps1.executeUpdate() > 0) {
//					ret = "Success";
//				}
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//			ret = e.getMessage();
//		} finally {
//			close(ps);
//			close(ps1);
//		}
//		return ret;
//	}
//
//	public String editStudent(Student student, String column, String value) {
//		String ret = null;
//		PreparedStatement ps = null;
//		String sql;
//		if (column.equals("studentNumber"))
//			sql = "UPDATE student SET " + column + "=? WHERE user_ID=?";
//		else
//			sql = "UPDATE `user` SET " + column + "=? WHERE ID=?";
//
//		try {
//			checkConn();
//			ps = preparedStatement(sql, value, student.getStudentId());
//			if (ps.executeUpdate() > 0) {
//				ret = "Success";
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//			ret = e.getMessage();
//		} finally {
//			close(ps);
//		}
//		return ret;
//	}
//
//	public String deleteAdmin(Admin adm) {
//		String ret = null;
//		PreparedStatement ps = null, ps1 = null;
//		final String sql_admin = "DELETE FROM admin where user_ID=?";
//		final String sql_user = "DELETE FROM `user` WHERE ID=?";
//		try {
//			checkConn();
//			ps = preparedStatement(sql_admin, adm.getAdminId());
//			if (ps.executeUpdate() > 0) {
//				ret = "Success";
//			}
//			if (ret.equals("Success")) {
//				ps1 = preparedStatement(sql_user, adm.getAdminId());
//				if (ps1.executeUpdate() > 0) {
//					ret = "Success";
//				}
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//			ret = e.getMessage();
//		} finally {
//			close(ps);
//			close(ps1);
//		}
//		return ret;
//	}
//
//	public Integer insertAdmin(Admin adm) {
//		PreparedStatement ps = null, ps1 = null;
//		ResultSet rs = null;
//		int last_inserted_id = 0;
//		final String sql_user = "INSERT INTO `user`(username,`password`,firstname,lastname) VALUES(?,?,?,?)";
//		final String sql_admin = "INSERT INTO admin(adminNumber,user_ID) VALUES(?,?)";
//		try {
//			checkConn();
//			ps = preparedStatement(Statement.RETURN_GENERATED_KEYS, sql_user, adm.getUserName(), adm.getPassword(),
//					adm.getFirstName(), adm.getLastName());
//
//			if (ps.executeUpdate() > 0) {
//				rs = ps.getGeneratedKeys();
//				if (rs.next()) {
//					last_inserted_id = rs.getInt(1);
//				}
//				ps1 = preparedStatement(sql_admin, adm.getAdminCode(), last_inserted_id);
//				ps1.executeUpdate();
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		} finally {
//			close(ps);
//			close(ps1);
//			close(rs);
//		}
//		return last_inserted_id;
//	}
//
//	public List<Admin> getAdmins() {
//		List<Admin> ret = null;
//		PreparedStatement ps = null;
//		ResultSet rs = null;
//		final String sql = "SELECT * FROM `admin` as a LEFT JOIN `user` as u on a.user_ID = u.ID";
//		List<Object> params = new ArrayList<>();
//		try {
//			checkConn();
//			ps = preparedStatement(sql, params.toArray());
//			rs = ps.executeQuery();
//			while (rs.next()) {
//				if (ret == null) {
//					ret = new ArrayList<>();
//				}
//				Admin oneadm = new Admin(rs.getInt("user_ID"), rs.getString("adminNumber"), rs.getString("username"),
//						rs.getString("password"), rs.getString("firstname"), rs.getString("lastname"));
//				ret.add(oneadm);
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		} finally {
//			close(ps);
//			close(rs);
//		}
//		return ret;
//	}
//
//	public String editAdmin(Admin admin, String column, String value) {
//		String ret = null;
//		PreparedStatement ps = null;
//		String sql;
//		if (column.equals("adminNumber"))
//			sql = "UPDATE admin SET " + column + "=? WHERE user_ID=?";
//		else
//			sql = "UPDATE `user` SET " + column + "=? WHERE ID=?";
//
//		try {
//			checkConn();
//			ps = preparedStatement(sql, value, admin.getAdminId());
//			if (ps.executeUpdate() > 0) {
//				ret = "Success";
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//			ret = e.getMessage();
//		} finally {
//			close(ps);
//		}
//		return ret;
//	}
//
//	public List<Answer> getAnswerForQuestion(Question q) {
//		List<Answer> ret = null;
//		PreparedStatement ps = null;
//		ResultSet rs = null;
//		final String sql = "SELECT * FROM answer WHERE question_ID=?";
//		try {
//			checkConn();
//			ps = preparedStatement(sql, q.getId());
//			rs = ps.executeQuery();
//			while (rs.next()) {
//				if (ret == null) {
//					ret = new ArrayList<>();
//				}
//				Answer oneans = new Answer(rs.getInt("ID"), rs.getString("answer"), rs.getInt("correctAnswer"), q);
//				ret.add(oneans);
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		} finally {
//			close(rs);
//			close(ps);
//		}
//		return ret;
//	}
//
//	public List<Question> getQuestionList() {
//		List<Question> ret = null;
//		PreparedStatement ps = null, ps1 = null, ps2 = null;
//		ResultSet rs = null;
//		ResultSet rs1 = null;
//		ResultSet rs2 = null;
//		final String sql_question = "SELECT * FROM question";
//		final String sql_test = "SELECT * FROM test WHERE ID=?";
//		final String sql_course = "SELECT * FROM course WHERE ID=?";
//		List<Object> params = new ArrayList<>();
//		try {
//			checkConn();
//			ps = preparedStatement(sql_question, params.toArray());
//			rs = ps.executeQuery();
//			while (rs.next()) {
//				if (rs1 == null) {
//					ret = new ArrayList<>();
//				}
//				ps1 = preparedStatement(sql_test, rs.getInt("test_ID"));
//				rs1 = ps1.executeQuery();
//				if (rs1.next()) {
//					ps2 = preparedStatement(sql_course, rs1.getInt("course_ID"));
//					rs2 = ps2.executeQuery();
//					if (rs2.next()) {
//						Course c = new Course(rs2.getInt("ID"), rs2.getString("courseName"),
//								rs2.getString("courseCode"));
//						Test t = new Test(rs1.getInt("ID"), rs1.getString("testName"), rs1.getString("testType"), c,
//								rs1.getInt("isEnable"), rs1.getInt("durationTime"));
//						Question q = new Question(rs.getInt("ID"), rs.getString("question"), rs.getInt("point"), t);
//						ret.add(q);
//					}
//				}
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		} finally {
//			close(rs);
//			close(rs1);
//			close(rs2);
//			close(ps);
//			close(ps1);
//			close(ps2);
//		}
//		return ret;
//	}
//
//	public String deleteQA(Question q) {
//		String ret = null;
//		PreparedStatement ps = null, ps1 = null;
//		final String sql_question = "DELETE FROM question WHERE ID=?";
//		final String sql_answer = "DELETE FROM answer WHERE question_ID=?";
//		try {
//			checkConn();
//			ps = preparedStatement(sql_question, q.getId());
//			if (ps.executeUpdate() > 0) {
//				ret = "Success";
//				ps1 = preparedStatement(sql_answer, q.getId());
//				ps1.executeUpdate();
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//			ret = e.getMessage();
//		} finally {
//			close(ps);
//			close(ps1);
//		}
//		return ret;
//	}
//
//	public String insertQA(Question ques, String a1, String a2, String a3, String a4, String cA) {
//		String ret = null;
//		PreparedStatement ps = null, ps1 = null, ps2 = null, ps3 = null, ps4 = null;
//		ResultSet rs = null;
//		int aOn_qid = 0;
//		final String sql_question = "INSERT INTO question(question, point, test_ID) values(?, ?, ?)";
//		final String sql_answer = "INSERT INTO answer(answer, correctAnswer, question_ID) values(?, ?, ?)";
//		try {
//			checkConn();
//			ps = preparedStatement(Statement.RETURN_GENERATED_KEYS, sql_question, ques.getQustion(), ques.getPoint(),
//					ques.getTest().getId());
//			if (ps.executeUpdate() > 0) {
//				rs = ps.getGeneratedKeys();
//				if (rs.next()) {
//					aOn_qid = rs.getInt(1);
//				}
//				if (cA.equals("1")) {
//					ps1 = preparedStatement(sql_answer, a1, 1, aOn_qid);
//					ps2 = preparedStatement(sql_answer, a2, 0, aOn_qid);
//					ps3 = preparedStatement(sql_answer, a3, 0, aOn_qid);
//					ps4 = preparedStatement(sql_answer, a4, 0, aOn_qid);
//				} else if (cA.equals("2")) {
//					ps1 = preparedStatement(sql_answer, a1, 0, aOn_qid);
//					ps2 = preparedStatement(sql_answer, a2, 1, aOn_qid);
//					ps3 = preparedStatement(sql_answer, a3, 0, aOn_qid);
//					ps4 = preparedStatement(sql_answer, a4, 0, aOn_qid);
//				} else if (cA.equals("3")) {
//					ps1 = preparedStatement(sql_answer, a1, 0, aOn_qid);
//					ps2 = preparedStatement(sql_answer, a2, 0, aOn_qid);
//					ps3 = preparedStatement(sql_answer, a3, 1, aOn_qid);
//					ps4 = preparedStatement(sql_answer, a4, 0, aOn_qid);
//				} else if (cA.equals("4")) {
//					ps1 = preparedStatement(sql_answer, a1, 0, aOn_qid);
//					ps2 = preparedStatement(sql_answer, a2, 0, aOn_qid);
//					ps3 = preparedStatement(sql_answer, a3, 0, aOn_qid);
//					ps4 = preparedStatement(sql_answer, a4, 1, aOn_qid);
//				}
//				ps1.executeUpdate();
//				ps2.executeUpdate();
//				ps3.executeUpdate();
//				ps4.executeUpdate();
//				ret = "Success";
//
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//			ret = e.getMessage();
//		} finally {
//			close(rs);
//			close(ps);
//			close(ps1);
//			close(ps2);
//			close(ps3);
//			close(ps4);
//		}
//		return ret;
//	}
//
//	public String updateQA(Question ques, String a1, String a2, String a3, String a4, String cA) {
//		String ret = null;
//		PreparedStatement ps = null, ps1 = null, ps2 = null, ps3 = null, ps4 = null;
//		final String sql_question = "UPDATE question SET question=?, point=?, test_ID=? WHERE ID=?";
//		final String sql_answer1 = "UPDATE answer SET answer=?, correctAnswer=? WHERE question_ID=? and ID=(SELECT ID FROM (select * from answer) as ANS where question_ID=? limit 0,1)";
//		final String sql_answer2 = "UPDATE answer SET answer=?, correctAnswer=? WHERE question_ID=? and ID=(SELECT ID FROM (select * from answer) as ANS where question_ID=? limit 1,1)";
//		final String sql_answer3 = "UPDATE answer SET answer=?, correctAnswer=? WHERE question_ID=? and ID=(SELECT ID FROM (select * from answer) as ANS where question_ID=? limit 2,1)";
//		final String sql_answer4 = "UPDATE answer SET answer=?, correctAnswer=? WHERE question_ID=? and ID=(SELECT ID FROM (select * from answer) as ANS where question_ID=? limit 3,1)";
//		try {
//			checkConn();
//			ps = preparedStatement(sql_question, ques.getQustion(), ques.getPoint(), ques.getTest().getId(),
//					ques.getId());
//			if (ps.executeUpdate() > 0) {
//				if (cA.equals("1")) {
//					ps1 = preparedStatement(sql_answer1, a1, 1, ques.getId(), ques.getId());
//					ps2 = preparedStatement(sql_answer2, a2, 0, ques.getId(), ques.getId());
//					ps3 = preparedStatement(sql_answer3, a3, 0, ques.getId(), ques.getId());
//					ps4 = preparedStatement(sql_answer4, a4, 0, ques.getId(), ques.getId());
//				} else if (cA.equals("2")) {
//					ps1 = preparedStatement(sql_answer1, a1, 0, ques.getId(), ques.getId());
//					ps2 = preparedStatement(sql_answer2, a2, 1, ques.getId(), ques.getId());
//					ps3 = preparedStatement(sql_answer3, a3, 0, ques.getId(), ques.getId());
//					ps4 = preparedStatement(sql_answer4, a4, 0, ques.getId(), ques.getId());
//				} else if (cA.equals("3")) {
//					ps1 = preparedStatement(sql_answer1, a1, 0, ques.getId(), ques.getId());
//					ps2 = preparedStatement(sql_answer2, a2, 0, ques.getId(), ques.getId());
//					ps3 = preparedStatement(sql_answer3, a3, 1, ques.getId(), ques.getId());
//					ps4 = preparedStatement(sql_answer4, a4, 0, ques.getId(), ques.getId());
//				} else if (cA.equals("4")) {
//					ps1 = preparedStatement(sql_answer1, a1, 0, ques.getId(), ques.getId());
//					ps2 = preparedStatement(sql_answer2, a2, 0, ques.getId(), ques.getId());
//					ps3 = preparedStatement(sql_answer3, a3, 0, ques.getId(), ques.getId());
//					ps4 = preparedStatement(sql_answer4, a4, 1, ques.getId(), ques.getId());
//				}
//				ps1.executeUpdate();
//				ps2.executeUpdate();
//				ps3.executeUpdate();
//				ps4.executeUpdate();
//				ret = "Success";
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//			ret = e.getMessage();
//		} finally {
//			close(ps);
//		}
//		return ret;
//	}
//
//	public List<Test> getTestList() {
//		List<Test> ret = null;
//		PreparedStatement ps = null, ps1 = null;
//		ResultSet rs = null, rs1 = null;
//		final String sql_test = "SELECT * FROM test";
//		String sql_course = "SELECT * FROM course WHERE ID=?";
//		List<Object> params = new ArrayList<>();
//		try {
//			checkConn();
//			ps = preparedStatement(sql_test, params.toArray());
//			rs = ps.executeQuery();
//			while (rs.next()) {
//				if (ret == null) {
//					ret = new ArrayList<>();
//				}
//				ps1 = preparedStatement(sql_course, rs.getInt("course_ID"));
//				rs1 = ps1.executeQuery();
//				if (rs1.next()) {
//					Course cour = new Course(rs1.getInt("ID"), rs1.getString("courseName"),
//							rs1.getString("courseCode"));
//					Test onetest = new Test(rs.getInt("ID"), rs.getString("testName"), rs.getString("testType"), cour,
//							rs.getInt("isEnable"), rs.getInt("durationTime"));
//					ret.add(onetest);
//				}
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		} finally {
//			close(ps);
//			close(rs);
//		}
//		return ret;
//	}
//
//	public String deleteTest(Test test) {
//		String ret = null;
//		PreparedStatement ps = null;
//		final String sql = "DELETE FROM TEST where ID=?";
//		try {
//			checkConn();
//			ps = preparedStatement(sql, test.getId());
//			if (ps.executeUpdate() > 0) {
//				ret = "Success";
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//			ret = e.getMessage();
//		} finally {
//			close(ps);
//		}
//
//		return ret;
//	}
//
//	public String updateTest(Test test) {
//		String ret = null;
//		PreparedStatement ps = null;
//		final String sql = "UPDATE test SET testName=?, testType=?, course_ID=?, isEnable=?, durationTime=?, courseCode=? WHERE ID=?";
//		try {
//			checkConn();
//			ps = preparedStatement(sql, test.getTestName(), test.getTestType(), test.getCourse().getCourseId(),
//					test.getIsEnable(), test.getDurationTime(), test.getCourse().getCourseCode(), test.getId());
//			if (ps.executeUpdate() > 0) {
//				ret = "Success";
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//			ret = e.getMessage();
//		} finally {
//			close(ps);
//		}
//		return ret;
//	}
//
//	public String insertTest(Test test) {
//		String ret = null;
//		PreparedStatement ps = null;
//		final String sql = "INSERT INTO test(testName, testType, course_ID, isEnable, durationTime, courseCode) values (?, ?, ?, ?, ?, ?)";
//		try {
//			checkConn();
//			ps = preparedStatement(sql, test.getTestName(), test.getTestType(), test.getCourse().getCourseId(),
//					test.getIsEnable(), test.getDurationTime(), test.getCourse().getCourseCode());
//			if (ps.executeUpdate() > 0) {
//				ret = "Success";
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//			ret = e.getMessage();
//		} finally {
//			close(ps);
//		}
//		return ret;
//	}
//
//	public Course getFacultyCourse(Integer userid) {
//		Course ret = null;
//		PreparedStatement ps = null;
//		ResultSet rs = null;
//		final String sql = "SELECT * FROM `course` WHERE ID=(SELECT course_ID from faculty WHERE user_ID=?)";
//		try {
//			checkConn();
//			ps = preparedStatement(sql, userid);
//			rs = ps.executeQuery();
//			if (rs.next()) {
//				ret = new Course(rs.getInt("ID"), rs.getString("courseName"), rs.getString("courseCode"));
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		} finally {
//			close(ps);
//			close(rs);
//		}
//		return ret;
//	}
//
//	public String editCourseGrade(String studentCode, String grade, String type) {
//		String ret = null;
//		PreparedStatement ps = null;
//		String sql = null;
//		if (type.equals("homework"))
//			sql = "UPDATE coursegrade SET homework=? WHERE studentNumber=?";
//		else
//			sql = "UPDATE coursegrade SET attendance=? WHERE studentNumber=?";
//		try {
//			checkConn();
//			ps = preparedStatement(sql, Double.parseDouble(grade), studentCode);
//			if (ps.executeUpdate() > 0) {
//				ret = "Success";
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//			ret = e.getMessage();
//		} finally {
//			close(ps);
//		}
//		return ret;
//	}
//
//	public Double getMidPoint(Course course) {
//		Double ret = null;
//		PreparedStatement ps = null;
//		ResultSet rs = null;
//		final String sql = "select sum(point) as point from question where test_ID=(select ID from test where course_ID=? and testType=\"MIDTERM\")";
//		try {
//			checkConn();
//			ps = preparedStatement(sql, course.getCourseId());
//			rs = ps.executeQuery();
//			if (rs.next()) {
//				ret = rs.getDouble("point");
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		} finally {
//			close(ps);
//			close(rs);
//		}
//		return ret;
//	}
//
//	public Double getFinalPoint(Course course) {
//		Double ret = null;
//		PreparedStatement ps = null;
//		ResultSet rs = null;
//		final String sql = "select sum(point) as point from question where test_ID=(select ID from test where course_ID=? and testType=\"FINAL\")";
//		try {
//			checkConn();
//			ps = preparedStatement(sql, course.getCourseId());
//			rs = ps.executeQuery();
//			if (rs.next()) {
//				ret = rs.getDouble("point");
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		} finally {
//			close(ps);
//			close(rs);
//		}
//		return ret;
//	}
//
//	public String checkMid(Course course) {
//		String ret = null;
//		PreparedStatement ps = null;
//		ResultSet rs = null;
//		final String sql = "SELECT * FROM test WHERE course_ID=? and testType=\"MIDTERM\"";
//		try {
//			checkConn();
//			ps = preparedStatement(sql, course.getCourseId());
//			rs = ps.executeQuery();
//			if (rs.next()) {
//				ret = rs.getString("testType");
//			} else {
//				ret = "No";
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		} finally {
//			close(rs);
//			close(ps);
//		}
//		return ret;
//	}
//
//	public String checkFinal(Course course) {
//		String ret = null;
//		PreparedStatement ps = null;
//		ResultSet rs = null;
//		final String sql = "SELECT * FROM test WHERE course_ID=? and testType=\"FINAL\"";
//		try {
//			checkConn();
//			ps = preparedStatement(sql, course.getCourseId());
//			rs = ps.executeQuery();
//			if (rs.next()) {
//				ret = rs.getString("testType");
//			} else {
//				ret = "No";
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		} finally {
//			close(rs);
//			close(ps);
//		}
//		return ret;
//	}

}
