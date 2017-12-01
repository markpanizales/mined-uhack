package com.pocketmarket.mined.di.components;

import com.pocketmarket.mined.data.ProductPaymentManager;
import com.pocketmarket.mined.data.SharedPrefReference;
import com.pocketmarket.mined.di.modules.ProductPaymentModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by mark on 12/1/17.
 */
@Singleton
@Component(modules = ProductPaymentModule.class)
public interface ProductPaymentComponent {
    ProductPaymentManager getProductPaymentManager();

    SharedPrefReference getSharedPrefReference();
}
