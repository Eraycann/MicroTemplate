package org.kafka.repository;

import org.kafka.model.Payment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long>{

    /*
    1. existsByPaymentMethodIdAndPaymentDate(Long paymentMethodId, Date paymentDate)
    Nerede kullanılıyor?
    Create (yeni ödeme) işlemi sırasında.

    Ne yapıyor?
    Veritabanında paymentMethodId ve paymentDate ile eşleşen bir kayıt var mı diye kontrol ediyor.

    Amaç?
    Aynı ödeme yöntemi ile aynı gün birden fazla ödeme yapılmasını engellemek.

    Yani:
    Eğer bu metot true dönerse, aynı gün ve aynı ödeme metodu ile zaten bir ödeme var demektir; bu durumda yeni ödeme oluşturulamaz ve bir validasyon hatası fırlatılır.
    * */
    boolean existsByPaymentMethodIdAndPaymentDate(Long paymentMethodId, Date paymentDate);

    /*
    2. existsByPaymentMethodIdAndPaymentDateAndIdNot(Long paymentMethodId, Date paymentDate, Long id)
    Nerede kullanılıyor?
    Update (var olan ödemeyi güncelleme) işlemi sırasında.

    Ne yapıyor?
    Veritabanında paymentMethodId ve paymentDate eşleşen, ancak güncellenmekte olan id dışındaki bir kayıt var mı diye kontrol ediyor.

    Amaç?
    Örneğin, bir ödeme kaydını güncellerken, aynı gün ve aynı ödeme metodu ile başka bir ödeme kaydı olup olmadığını kontrol etmek.

    Yani:
    Eğer bu metot true dönerse, güncellenmekte olan kaydın dışındaki başka bir kayıt var demektir ve aynı gün aynı ödeme metodu ile birden fazla ödeme olması engellenir.
    * */
    boolean existsByPaymentMethodIdAndPaymentDateAndIdNot(Long paymentMethodId, Date paymentDate, Long id);

    Page<Payment> findByAmountGreaterThan(Float amount, Pageable pageable);
}