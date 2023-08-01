import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.springframework.boot.gradle.tasks.run.BootRun

// plugins 블록만으로 apply 가능
plugins {

	// 스프링부트 플러그인
	// @see https://docs.spring.io/spring-boot/docs/current/gradle-plugin/reference/htmlsingle/
	// @see https://plugins.gradle.org/plugin/org.springframework.boot
	id("org.springframework.boot")

	// 스프링 의존성 관리 플러그인
	// bom에 기본적으로 정의된 의존성 버전을 사용하여 생략가능
	// @see https://docs.spring.io/dependency-management-plugin/docs/current-SNAPSHOT/reference/html/
	// @see https://github.com/spring-gradle-plugins/dependency-management-plugin
	 id("io.spring.dependency-management")

	// 자바 플러그인 (springboot 플러그인에 포함)
	// @see https://docs.gradle.org/current/userguide/java_plugin.html
	// java

	// 코틀린 버전을 정의한 플러그인
	// @see https://kotlinlang.org/docs/jvm-create-project-with-spring-boot.html#explore-the-project-gradle-build-file
	kotlin("jvm")

	// 코틀린 스프링 컴파일 플러그인
	// open 수정자를 추가하기 위한 플러그인
	// 스프링프레임워크나 AOP 라이브러리를 사용하기 위해서는 상속이 필요하지만, 코틀린 클래스는 final을 강요해 불편한다
	// @see https://kotlinlang.org/docs/all-open-plugin.html
	kotlin("plugin.spring")
}

group = "com.bgpark"
version = "0.0.1-SNAPSHOT"

java {
	sourceCompatibility = JavaVersion.VERSION_17
}

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter")

	// 코틀린 reflect 라이브러리
	implementation("org.jetbrains.kotlin:kotlin-reflect")

	// 코루틴
	implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core")

	// 스프링 웹 MVC - web은 톰캣 서버, webflux는 netty 서버 사용
	// implementation("org.springframework.boot:spring-boot-starter-web")

	// 스프링 웹 플럭스
	implementation("org.springframework.boot:spring-boot-starter-webflux")

	// 코틀린 리액터
	implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")

	implementation("org.springframework.boot:spring-boot-starter-actuator")

	testImplementation("org.springframework.boot:spring-boot-starter-test")
}

// 코틀린 컴파일러에 추가 아규먼트를 정의한 블록
tasks.withType<KotlinCompile> {// KolinCompile 타입의 task를 선택
	println("java version : ${JavaVersion.VERSION_17}")
	println("springBootDependencyManagementVersion : ${project.properties.get("springBootDependencyManagementVersion")}")

	kotlinOptions {
		freeCompilerArgs += "-Xjsr305=strict" // JSR-305 주석 지원처리를 활성화
		jvmTarget = JavaVersion.VERSION_17.toString() // 컴파일된 바이트 코드의 대상 JVM 옵션을 JVM 17 버전으로 설정
	}
}

// JUnit 5 플랫폼 사용하기 위한 코드
tasks.withType<Test> {
	useJUnitPlatform()
}

// 스프링 버전 추가
// gradle.properties에서도 관리 가능
//ext["spring.version"] = "2.7.3"

tasks.named<BootRun>("bootRun") {
	println("bootRun 실행!")
	println("spring version: ${ext["spring.version"]}")
	println("active profile : ${System.getProperty("spring.profiles.active")}")

	// active 프로필을 지정하기 위해서는 아래 코드가 필요하다
	systemProperty("spring.profiles.active", System.getProperty("spring.profiles.active"))
}

tasks.jar {
	// plain jar 생성 방지, plain jar는 외부 의존성을 제외한 파일
	// https://velog.io/@chappse6/%EC%98%A4%EB%8A%98%EC%9D%98-%EC%98%A4%EB%A5%98-%EA%B8%B0%EB%B3%B8-manifest-%EC%86%8D%EC%84%B1%EC%9D%B4-%EC%97%86%EC%8A%B5%EB%8B%88%EB%8B%A4
	enabled = false

	when (System.getProperties().getProperty("build_stage")) {
		// 로컬
		"local" -> {
			println("local!")
		}
		// 개발
		"dev" -> {
			println("dev!")
		}
		// 운영
		else -> {
			println("prod!")
		}
	}

	copy {
		from("./script/code-deploy")
		into("./build/libs/script/code-deploy")
	}

	copy {
		from("./appspec.yml")
		into("./build/libs")
	}
}

tasks.test {
	println("현재 자바 버전 : ${JavaVersion.current()}")
}

tasks.bootJar {
	// 젠킨스에서 해당이름으로 배포 중
	archiveFileName.set("log-collector.jar")
}

tasks.register<Tar>("tar") {
	val src = "$buildDir/libs"
	compression = Compression.GZIP

	println("${project.name}.tar")

	archiveFileName.set("${project.name}.tar")
	destinationDirectory.set(buildDir)
	from(src) {
		include("**/*") // 압축할 파일 또는 디렉토리의 경로와 패턴을 지정하세요
	}
}
