import React, { useState } from 'react';
import { useTheme } from '../../context/ThemeContext';
import { CombinaIcon } from '../../components/brand/CombinaLogo';

function Cadastrar() {
  const { theme, toggleTheme } = useTheme();
  const dark = theme === 'dark';

  const [formData, setFormData] = useState({
    username: '',
    nomeCompleto: '',
    email: '',
    senha: '',
  });

  const [showPassword, setShowPassword] = useState(false);
  const [touched, setTouched] = useState(false);

  const passwordRules = {
    hasMinLength: formData.senha.length >= 6,
    hasUpperCase: /[A-Z]/.test(formData.senha),
    hasNumber: /[0-9]/.test(formData.senha),
    hasSpecialChar: /[!@#$%^&*(),.?":{}|<>]/.test(formData.senha),
  };

  const isPasswordValid = Object.values(passwordRules).every(Boolean);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData((prev) => ({ ...prev, [name]: value }));
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    setTouched(true);
    if (!isPasswordValid) return;
    console.log('Dados prontos para envio:', formData);
  };

  return (
    <div
      className="min-h-screen bg-[#FAFAF9] dark:bg-[#111111] text-[#171717] dark:text-[#F5F5F5] flex flex-col justify-between transition-colors duration-200 antialiased px-4 sm:px-6"
      style={{ fontFamily: 'Manrope, sans-serif' }}
    >
      {/* HEADER SUPERIOR */}
      <header className="py-4 flex items-center justify-between max-w-7xl w-full mx-auto">
        <a
          href="/home"
          className="flex items-center gap-2 group text-xs sm:text-sm font-semibold text-[#737373] dark:text-[#A3A3A3] hover:text-[#171717] dark:hover:text-white transition"
        >
          <span className="w-8 h-8 bg-white dark:bg-[#191919] border border-[#E5E5E5] dark:border-[#2A2A2A] rounded-lg flex items-center justify-center group-hover:border-[#171717] dark:group-hover:border-[#737373] transition">
            <svg className="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
              <path strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="M10 19l-7-7m0 0l7-7m-7 7h18" />
            </svg>
          </span>
          Voltar para a Home
        </a>

        <button
          onClick={toggleTheme}
          type="button"
          aria-label="Alternar tema"
          className="p-2 sm:p-2.5 rounded-xl border border-[#E5E5E5] dark:border-[#2A2A2A] bg-white dark:bg-[#191919] text-[#404040] dark:text-[#D4D4D4] hover:border-[#171717] dark:hover:border-[#737373] transition duration-150 flex items-center justify-center shadow-sm"
        >
          {dark ? (
            <svg className="w-4 h-4 sm:w-5 sm:h-5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
              <path strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="M12 3v1m0 16v1m9-9h-1M4 12H3m15.364 6.364l-.707-.707M6.343 6.343l-.707-.707m12.728 0l-.707.707M6.343 17.657l-.707.707M16 12a4 4 0 11-8 0 4 4 0 018 0z" />
            </svg>
          ) : (
            <svg className="w-4 h-4 sm:w-5 sm:h-5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
              <path strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="M20.354 15.354A9 9 0 018.646 3.646 9.003 9.003 0 0012 21a9.003 9.003 0 008.354-5.646z" />
            </svg>
          )}
        </button>
      </header>

      {/* CONTEÚDO CENTRAL */}
      <main className="flex-1 flex items-center justify-center py-8">
        <div className="w-full max-w-md">
          {/* LOGO E TÍTULO */}
          <div className="text-center mb-6 sm:mb-8">
            <div className="inline-flex mb-4">
              <CombinaIcon size={52} />
            </div>
            <h1 className="text-2xl sm:text-3xl font-extrabold text-[#171717] dark:text-white tracking-tight">
              Crie sua conta
            </h1>
            <p className="text-xs sm:text-sm text-[#737373] dark:text-[#A3A3A3] mt-1">
              Junte-se ao Combinaí e comece a combinar suas salas
            </p>
          </div>

          {/* CARD DO FORMULÁRIO */}
          <div className="bg-white dark:bg-[#191919] p-6 sm:p-8 rounded-2xl border border-[#E5E5E5] dark:border-[#2A2A2A] shadow-xl shadow-black/5 dark:shadow-none transition-colors duration-200">
            <form onSubmit={handleSubmit} className="space-y-4 sm:space-y-5">
              {/* USERNAME */}
              <div>
                <label className="block text-xs font-bold text-[#404040] dark:text-[#D4D4D4] uppercase tracking-wider mb-1.5">
                  Nome de usuário
                </label>
                <input
                  type="text"
                  name="username"
                  required
                  value={formData.username}
                  onChange={handleChange}
                  placeholder="ex: maria_da_silva"
                  className="w-full px-4 py-2.5 sm:py-3 rounded-xl bg-[#F5F5F5] dark:bg-[#242424] border border-[#E5E5E5] dark:border-[#333333] text-[#171717] dark:text-white text-sm placeholder-[#A3A3A3] focus:outline-none focus:border-[#171717] dark:focus:border-white focus:ring-1 focus:ring-[#171717] dark:focus:ring-white transition"
                />
              </div>

              {/* NOME COMPLETO */}
              <div>
                <label className="block text-xs font-bold text-[#404040] dark:text-[#D4D4D4] uppercase tracking-wider mb-1.5">
                  Nome completo
                </label>
                <input
                  type="text"
                  name="nomeCompleto"
                  required
                  value={formData.nomeCompleto}
                  onChange={handleChange}
                  placeholder="ex: Maria da Silva Gonçalves"
                  className="w-full px-4 py-2.5 sm:py-3 rounded-xl bg-[#F5F5F5] dark:bg-[#242424] border border-[#E5E5E5] dark:border-[#333333] text-[#171717] dark:text-white text-sm placeholder-[#A3A3A3] focus:outline-none focus:border-[#171717] dark:focus:border-white focus:ring-1 focus:ring-[#171717] dark:focus:ring-white transition"
                />
              </div>

              {/* EMAIL */}
              <div>
                <label className="block text-xs font-bold text-[#404040] dark:text-[#D4D4D4] uppercase tracking-wider mb-1.5">
                  E-mail
                </label>
                <input
                  type="email"
                  name="email"
                  required
                  value={formData.email}
                  onChange={handleChange}
                  placeholder="seu.email@empresa.com"
                  className="w-full px-4 py-2.5 sm:py-3 rounded-xl bg-[#F5F5F5] dark:bg-[#242424] border border-[#E5E5E5] dark:border-[#333333] text-[#171717] dark:text-white text-sm placeholder-[#A3A3A3] focus:outline-none focus:border-[#171717] dark:focus:border-white focus:ring-1 focus:ring-[#171717] dark:focus:ring-white transition"
                />
              </div>

              {/* SENHA */}
              <div>
                <label className="block text-xs font-bold text-[#404040] dark:text-[#D4D4D4] uppercase tracking-wider mb-1.5">
                  Senha
                </label>

                <div className="relative">
                  <input
                    type={showPassword ? 'text' : 'password'}
                    name="senha"
                    required
                    value={formData.senha}
                    onChange={(e) => {
                      handleChange(e);
                      if (!touched) setTouched(true);
                    }}
                    placeholder="••••••••"
                    className={`w-full pl-4 pr-11 py-2.5 sm:py-3 rounded-xl bg-[#F5F5F5] dark:bg-[#242424] border text-[#171717] dark:text-white text-sm placeholder-[#A3A3A3] focus:outline-none transition ${
                      touched && !isPasswordValid
                        ? 'border-rose-400 dark:border-rose-500 focus:ring-1 focus:ring-rose-500'
                        : 'border-[#E5E5E5] dark:border-[#333333] focus:border-[#171717] dark:focus:border-white focus:ring-1 focus:ring-[#171717] dark:focus:ring-white'
                    }`}
                  />

                  <button
                    type="button"
                    onClick={() => setShowPassword((prev) => !prev)}
                    aria-label={showPassword ? 'Ocultar senha' : 'Exibir senha'}
                    className="absolute right-3 top-1/2 -translate-y-1/2 text-[#A3A3A3] hover:text-[#171717] dark:hover:text-white transition p-1 rounded-lg focus:outline-none"
                  >
                    {showPassword ? (
                      <svg className="w-5 h-5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                        <path strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="M13.875 18.825A10.05 10.05 0 0112 19c-7 0-11-8-11-8a18.45 18.45 0 015.06-5.94M9.9 4.24A9.12 9.12 0 0112 4c7 0 11 8 11 8a18.5 18.5 0 01-2.16 3.19m-6.72-1.07a3 3 0 11-4.24-4.24M1 1l22 22" />
                      </svg>
                    ) : (
                      <svg className="w-5 h-5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                        <path strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="M15 12a3 3 0 11-6 0 3 3 0 016 0z" />
                        <path strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="M2.458 12C3.732 7.943 7.523 5 12 5c4.478 0 8.268 2.943 9.542 7-1.274 4.057-5.064 7-9.542 7-4.477 0-8.268-2.943-9.542-7z" />
                      </svg>
                    )}
                  </button>
                </div>

                {/* CHECKLIST DE VALIDAÇÃO DA SENHA */}
                <div className="mt-3 p-3 bg-[#F5F5F5] dark:bg-[#1F1F1F] rounded-xl border border-[#E5E5E5] dark:border-[#2A2A2A] space-y-1.5 text-xs">
                  <p className="font-bold text-[#737373] dark:text-[#A3A3A3] mb-2">Requisitos da senha:</p>

                  {[
                    { ok: passwordRules.hasMinLength, label: 'Mínimo de 6 caracteres' },
                    { ok: passwordRules.hasUpperCase, label: 'Pelo menos uma letra maiúscula' },
                    { ok: passwordRules.hasNumber, label: 'Pelo menos um número' },
                    { ok: passwordRules.hasSpecialChar, label: 'Pelo menos um caractere especial (!@#$%...)' },
                  ].map((rule) => (
                    <div
                      key={rule.label}
                      className={`flex items-center gap-2 ${rule.ok ? 'text-emerald-600 dark:text-emerald-400' : 'text-[#A3A3A3] dark:text-[#666666]'}`}
                    >
                      <span>{rule.ok ? '✓' : '○'}</span>
                      <span>{rule.label}</span>
                    </div>
                  ))}
                </div>
              </div>

              {/* BOTÃO DE SUBMIT */}
              <button
                type="submit"
                disabled={formData.senha.length > 0 && !isPasswordValid}
                className="w-full py-3.5 px-4 bg-[#171717] dark:bg-white hover:bg-[#404040] dark:hover:bg-[#E5E5E5] disabled:bg-[#D4D4D4] dark:disabled:bg-[#2A2A2A] disabled:cursor-not-allowed active:scale-[0.99] text-white dark:text-[#171717] font-bold text-sm rounded-xl shadow-lg shadow-black/10 dark:shadow-none transition duration-150 mt-2"
              >
                Criar minha conta
              </button>
            </form>

            {/* JÁ POSSUI CONTA */}
            <div className="mt-6 text-center text-xs text-[#737373] dark:text-[#A3A3A3] border-t border-[#E5E5E5] dark:border-[#2A2A2A] pt-5">
              Já tem uma conta?{' '}
              <a href="/signin" className="text-[#171717] dark:text-white font-bold hover:underline">
                Faça login
              </a>
            </div>
          </div>
        </div>
      </main>

      {/* RODAPÉ */}
      <footer className="py-6 text-center text-xs text-[#A3A3A3] dark:text-[#525252]">
        <p>© {new Date().getFullYear()} Combinaí. Todos os direitos reservados.</p>
      </footer>
    </div>
  );
}

export default Cadastrar;