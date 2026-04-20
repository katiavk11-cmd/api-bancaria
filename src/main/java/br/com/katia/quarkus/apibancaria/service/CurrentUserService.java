package br.com.katia.quarkus.apibancaria.service;

import br.com.katia.quarkus.apibancaria.model.LoggedUser;

public interface CurrentUserService {

    LoggedUser getLoggedUser();

}