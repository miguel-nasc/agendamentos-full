import React, { createContext, useContext, useState, useEffect } from 'react';
import api from '../services/api';

const AuthContext = createContext({});

export const AuthProvider = ({ children }) => {
  const [user, setUser] = useState(null);
  const [loading, setLoading] = useState(true);

  // Recupera as credenciais salvas ao carregar a aplicação
  useEffect(() => {
    const storedToken = localStorage.getItem('@ReserveSpace:token');
    const storedUser = localStorage.getItem('@ReserveSpace:user');

    if (storedToken && storedUser) {
      setUser({ username: storedUser, token: storedToken });
    }

    setLoading(false);
  }, []);

  // Função de Login que chama o endpoint Spring Boot
  const signIn = async ({ username, password }) => {
    const response = await api.post('/auth/signin', { username, password });
    
    // Extrai o accessToken do JSON de resposta do Java
    const { accessToken } = response.data;

    // Salva no localStorage
    localStorage.setItem('@ReserveSpace:token', accessToken);
    localStorage.setItem('@ReserveSpace:user', username);

    // Atualiza o estado global
    setUser({ username, token: accessToken });

    return response.data;
  };

  // Função de Logout
  const signOut = () => {
    localStorage.removeItem('@ReserveSpace:token');
    localStorage.removeItem('@ReserveSpace:user');
    setUser(null);
  };

  return (
    <AuthContext.Provider
      value={{
        signed: !!user, // Retorna true se houver usuário autenticado
        user,
        loading,
        signIn,
        signOut,
      }}
    >
      {children}
    </AuthContext.Provider>
  );
};

// Hook personalizado para usar o AuthContext de forma simples
export const useAuth = () => {
  const context = useContext(AuthContext);
  if (!context) {
    throw new Error('useAuth deve ser utilizado dentro de um AuthProvider');
  }
  return context;
};