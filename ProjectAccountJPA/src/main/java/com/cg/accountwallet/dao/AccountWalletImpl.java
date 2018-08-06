package com.cg.accountwallet.dao;

import java.sql.Connection;



import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import com.cg.accountwallet.bean.Account;
import com.cg.accountwallet.exception.AccountWalletException;

public class AccountWalletImpl implements AccountWalletDao {

	EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
	 
	EntityManager em = emf.createEntityManager();
	public String createAccount(Account acc) throws AccountWalletException {
		em.getTransaction().begin();
		em.persist(acc);
		em.getTransaction().commit();
		return acc.getMobileNo();
	}

	public double showBalance(String mobileNo) throws AccountWalletException {
		String str="select a from Account a where a.mobileNo=?";
		TypedQuery<Account> query=em.createQuery(str,Account.class);
		query.setParameter(1,mobileNo);
		Account ac=query.getSingleResult();
		if(mobileNo.equals(ac.getMobileNo())) {
		return ac.getBalance();
		}else {
		throw new AccountWalletException("number doesnot exists");
		}
	}

	public Account deposit(String mobileNo, double depAmount) throws AccountWalletException {
		em.getTransaction().begin();
		String str="select a from Account a where a.mobileNo=?";
		TypedQuery<Account> query=em.createQuery(str,Account.class);
		query.setParameter(1,mobileNo);
		Account ac=query.getSingleResult();
		
		if(ac==null) {
		throw new AccountWalletException("does not exists");
		}
		double d=ac.getBalance()+depAmount;
		ac.setBalance(d);
		em.merge(ac);
		 
		 
		em.getTransaction().commit();
		return ac;
		 
	}

	public Account withdraw(String mobileNo, double withdraw) throws AccountWalletException {
		em.getTransaction().begin();
		String str="select a from Account a where a.mobileNo=?";
		TypedQuery<Account> query=em.createQuery(str,Account.class);
		query.setParameter(1,mobileNo);
		Account ac=query.getSingleResult();
		if(ac==null) {
		throw new AccountWalletException("does not exists");
		}
		double d=ac.getBalance()-withdraw;
		ac.setBalance(d);
		em.merge(ac);
		 
		 
		em.getTransaction().commit();
		return ac;
	}

	public Account printTransactionDetails(String mobileNo) throws AccountWalletException {
			String str="select a from Account a where a.mobileNo=?";
			TypedQuery<Account> query=em.createQuery(str,Account.class);
			query.setParameter(1,mobileNo);
			Account ac=query.getSingleResult();
			if(ac!=null) {
			return ac;
			}else {
			throw new AccountWalletException("number doesnot exists");
			}
			}
	

	 
	}
	



