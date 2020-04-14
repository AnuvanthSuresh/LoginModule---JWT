package com.stock.utils;

import java.security.Key;
import java.util.Date;
import java.util.Map;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;

@Service
public class TokenProperties {

	SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
	String secret = "Knight";
	byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(secret);
	Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());

	/*
	 * @author Anuvanth
	 * 
	 * Accepts Username and a map which contains user roles as of now sending in
	 * default user role acess
	 * 
	 * @Return String JWT
	 * 
	 */
	public String GenerateToken(String username, Map<String, Object> claims) {

		String Token = Jwts.builder().setClaims(claims).setSubject(username).setIssuer("KnightMachine")
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 15)) // Token Expiers in 3 Minutes
				.signWith(signatureAlgorithm, signingKey).compact();

		return Token;
	}

	/*
	 * @author Anuvanth
	 * 
	 * Accepts Token and the parameter required from the token method returns string
	 * of the required parameter from the token
	 */
	public String GetParameterFromToken(String Token, String Parameter) {

		try {
			Claims claims = Jwts.parser().setSigningKey(signingKey).parseClaimsJws(Token).getBody();

			if (Parameter.equalsIgnoreCase("username")) {
				return claims.getSubject();
			} else if (Parameter.equalsIgnoreCase("issuer")) {
				return claims.getIssuer();
			} else {
				return "invalid choice";
			}

		} catch (ExpiredJwtException exception) {
			return "JWT Expired - Exception Caught";

		} catch (SignatureException exc) {
			return "Signature Mismatch";
		} catch (Exception other) {
			other.printStackTrace();
			return "Other Type of exception in GetParameterFromToken Method";
		}
	}

	/*
	 * @author Anuvanth
	 * 
	 * Accepts token returns boolean corresponding to token validity
	 * 
	 */
	public Boolean ValidateToken(String Token) {
		try {
			Claims claims = Jwts.parser().setSigningKey(signingKey).parseClaimsJws(Token).getBody();
			return new Date().before(claims.getExpiration());
		} catch (ExpiredJwtException exception) {
			System.out.println("JWT Expired - Exception Caught");
			return false;

		} catch (SignatureException exc) {
			System.out.println("Signature Mismatch");
			return false;
		} catch (Exception other) {
			other.printStackTrace();
			System.out.println("Other Type of exception in GetParameterFromToken Method");
			return false;
		}
	}

}
