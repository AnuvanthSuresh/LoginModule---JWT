package com.stock.interceptors;

import java.security.Key;
import java.util.Date;

import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.DatatypeConverter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;

import com.stock.utils.TokenProperties;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;

public class AuthenticationInterceptor implements HandlerInterceptor {

	@Autowired
	TokenProperties tokenProps;

	SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
	String secret = "Knight";
	byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(secret);
	Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		String token = request.getHeader("Token");
		try {
			Claims claims = Jwts.parser().setSigningKey(signingKey).parseClaimsJws(token).getBody();
			return new Date().before(claims.getExpiration());
		} catch (ExpiredJwtException exception) {
			System.out.println("JWT Expired - Exception Caught");
			return false;

		} catch (SignatureException exc) {
			System.out.println("Signature Mismatch");
			return false;
		} catch (Exception other) {
			other.printStackTrace();
			System.out.println("Other Type of exception in INTERCEPTOR Method");
			return false;
		}
	}

}
