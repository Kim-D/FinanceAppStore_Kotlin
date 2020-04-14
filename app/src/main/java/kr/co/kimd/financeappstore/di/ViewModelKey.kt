package kr.co.kimd.financeappstore.di

import androidx.lifecycle.ViewModel
import dagger.MapKey
import kotlin.reflect.KClass

@MustBeDocumented   // 어노테이션이 공개 API 에 속하는지, API 문서의 클래스, 메서드 시그니처 포함 여부
@Target(    // 어노테이션 할 수 있는 요소를 지정한다
    AnnotationTarget.FUNCTION,
    AnnotationTarget.PROPERTY_GETTER,
    AnnotationTarget.PROPERTY_SETTER
)
@Retention(AnnotationRetention.RUNTIME) // 어노테이션을 컴파일 클래스로 저장할지, 런타임에 reflection 을 이용해 접근할지
@MapKey
annotation class ViewModelKey(val value: KClass<out ViewModel>)