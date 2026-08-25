import React from 'react';
import { useTheme } from '../../context/ThemeContext';
import { CombinaLogo } from '../../components/brand/CombinaLogo';

// Apenas 5 salas de demonstração para exibição na Home
const salasDestaque = [
  { id: 'SA-101', localizacao: 'Rua das Palmeiras, 1º andar, Prédio A', responsavel: 'Ana Carolina Ferreira', capacidade: 20 },
  { id: 'SA-130', localizacao: 'Rua das Palmeiras, 2º andar, Prédio A', responsavel: 'Gabriela Martins Costa', capacidade: 50 },
  { id: 'AU-301', localizacao: 'Avenida Central, 3º andar, Prédio E', responsavel: 'Juliana Costa Ribeiro', capacidade: 100 },
  { id: 'WS-601', localizacao: 'Rua das Camélias, 6º andar, Prédio J', responsavel: 'Leonardo Matos Xavier', capacidade: 10 },
  { id: 'EX-1001', localizacao: 'Rua da Consolação, 10º andar, Prédio N', responsavel: 'Vanessa Oliveira Nogueira', capacidade: 48 },
];

function Home() {
  const { theme, toggleTheme } = useTheme();
  const dark = theme === 'dark';

  return (
    <div
      className="min-h-screen bg-[#FAFAF9] dark:bg-[#111111] text-[#171717] dark:text-[#F5F5F5] transition-colors duration-200 antialiased overflow-x-hidden"
      style={{ fontFamily: 'Manrope, sans-serif' }}
    >
      {/* NAVBAR */}
      <nav className="sticky top-0 z-50 bg-[#FAFAF9]/90 dark:bg-[#111111]/90 backdrop-blur-md border-b border-[#E5E5E5] dark:border-[#2A2A2A] px-4 sm:px-6 py-3 sm:py-4 transition-colors duration-200">
        <div className="max-w-7xl mx-auto flex items-center justify-between gap-4">
          <a href="/home" className="shrink-0" aria-label="Combinaí - início">
            <CombinaLogo iconSize={36} textSize="text-xl" />
          </a>

          <div className="hidden md:flex items-center gap-8 font-semibold text-sm text-[#737373] dark:text-[#A3A3A3]">
            <a href="#destaques" className="hover:text-[#171717] dark:hover:text-white transition">Salas em Destaque</a>
            <a href="#como-funciona" className="hover:text-[#171717] dark:hover:text-white transition">Como Funciona</a>
          </div>

          <div className="flex items-center gap-1.5 sm:gap-3 shrink-0">
            <button
              onClick={toggleTheme}
              aria-label="Alternar tema"
              className="p-2 sm:p-2.5 rounded-xl border border-[#E5E5E5] dark:border-[#2A2A2A] bg-[#F5F5F5] dark:bg-[#1F1F1F] text-[#404040] dark:text-[#D4D4D4] hover:border-[#171717] dark:hover:border-[#737373] transition duration-150 flex items-center justify-center"
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

            <a href="/signin" className="px-2.5 py-1.5 sm:px-4 sm:py-2 text-xs sm:text-sm font-semibold text-[#404040] dark:text-[#D4D4D4] hover:text-[#171717] dark:hover:text-white transition">
              Login
            </a>
            <a href="/signup" className="px-3 py-1.5 sm:px-4 sm:py-2 text-xs sm:text-sm font-bold text-white bg-[#171717] dark:bg-white dark:text-[#171717] hover:bg-[#404040] dark:hover:bg-[#E5E5E5] active:scale-[0.98] rounded-xl shadow-md dark:shadow-none transition duration-150">
              Cadastrar
            </a>
          </div>
        </div>
      </nav>

      {/* HERO */}
      <section className="relative pt-10 pb-14 sm:pt-16 sm:pb-20 md:pt-24 md:pb-28 px-4 sm:px-6">
        <div className="max-w-7xl mx-auto grid md:grid-cols-2 gap-10 md:gap-16 items-center">
          <div>
            <span className="inline-flex items-center gap-2 px-3 py-1.5 bg-white dark:bg-[#191919] border border-[#E5E5E5] dark:border-[#2A2A2A] text-[#404040] dark:text-[#D4D4D4] font-bold text-[11px] sm:text-xs rounded-full mb-5">
              <span className="w-1.5 h-1.5 rounded-full bg-[#171717] dark:bg-white" />
              Ambientes corporativos
            </span>

            <h1 className="text-4xl sm:text-5xl md:text-6xl font-extrabold text-[#171717] dark:text-white leading-[1.04] tracking-[-0.055em] mb-5 sm:mb-6">
              Seu espaço.<br />
              <span className="text-[#737373] dark:text-[#A3A3A3]">Seu horário.</span><br />
              Combinaí.
            </h1>

            <p className="text-[#737373] dark:text-[#A3A3A3] text-sm sm:text-base md:text-lg leading-relaxed max-w-xl mb-7 sm:mb-8">
              Encontre a sala certa, escolha o horário e combine tudo em poucos passos.
              Simples para você, organizado para todos.
            </p>

            <div className="flex flex-col sm:flex-row gap-3 sm:gap-4">
              <a href="#destaques" className="px-6 py-3.5 bg-[#171717] dark:bg-white text-white dark:text-[#171717] hover:bg-[#404040] dark:hover:bg-[#E5E5E5] font-bold rounded-xl shadow-lg shadow-black/10 dark:shadow-none transition duration-150 text-center text-sm">
                Encontrar uma sala
              </a>
              <a href="/login" className="px-6 py-3.5 bg-white dark:bg-[#191919] border border-[#E5E5E5] dark:border-[#2A2A2A] hover:bg-[#F5F5F5] dark:hover:bg-[#222222] text-[#404040] dark:text-[#D4D4D4] font-bold rounded-xl transition duration-150 text-center text-sm">
                Acessar o Painel
              </a>
            </div>
          </div>

          {/* HERO CARD */}
          <div className="relative mt-2 md:mt-0">
            <div className="absolute -top-8 -right-8 w-32 h-32 rounded-full border border-[#E5E5E5] dark:border-[#2A2A2A] opacity-60" />
            <div className="absolute -bottom-8 -left-8 w-20 h-20 rounded-full border border-[#E5E5E5] dark:border-[#2A2A2A] opacity-60" />

            <div className="relative rounded-3xl overflow-hidden border border-[#E5E5E5] dark:border-[#2A2A2A] bg-white dark:bg-[#191919] shadow-2xl shadow-black/5 dark:shadow-black/20 p-5 sm:p-7">
              <div className="flex flex-wrap items-center justify-between gap-3 border-b border-[#E5E5E5] dark:border-[#2A2A2A] pb-5 mb-5">
                <div>
                  <span className="text-[10px] sm:text-xs font-bold text-[#737373] dark:text-[#A3A3A3] uppercase tracking-wider block mb-1">Espaço em destaque</span>
                  <h3 className="text-lg sm:text-xl font-extrabold text-[#171717] dark:text-white tracking-tight">Auditório AU-301</h3>
                </div>
                <span className="px-2.5 py-1 bg-[#F5F5F5] dark:bg-[#242424] text-[#404040] dark:text-[#D4D4D4] text-xs font-bold rounded-full border border-[#E5E5E5] dark:border-[#333333]">
                  Disponível
                </span>
              </div>

              <div className="flex items-center gap-2 mb-4">
                <div className="w-9 h-9 rounded-xl bg-[#F5F5F5] dark:bg-[#242424] flex items-center justify-center">
                  <svg className="w-4 h-4 text-[#404040] dark:text-[#D4D4D4]" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                    <path strokeLinecap="round" strokeLinejoin="round" strokeWidth="1.8" d="M17 20h5v-2a3 3 0 00-5.356-1.857M17 20H7m10 0v-2a3 3 0 00-5.356-1.857M7 20H2v-2a3 3 0 015.356-1.857M15 7a3 3 0 11-6 0 3 3 0 016 0z" />
                  </svg>
                </div>
                <div>
                  <p className="text-xs text-[#737373] dark:text-[#A3A3A3]">Capacidade</p>
                  <p className="text-sm font-bold text-[#171717] dark:text-white">100 pessoas</p>
                </div>
              </div>

              <p className="text-xs sm:text-sm text-[#404040] dark:text-[#D4D4D4] mb-2 font-semibold break-words">Avenida Central, 3º andar, Prédio E</p>
              <p className="text-xs text-[#A3A3A3] dark:text-[#737373] mb-6">Responsável: Juliana Costa Ribeiro</p>

              <a href="/login" className="block w-full py-3 text-center bg-[#171717] dark:bg-white hover:bg-[#404040] dark:hover:bg-[#E5E5E5] text-white dark:text-[#171717] font-bold text-sm rounded-xl transition duration-150">
                Combinar esta sala
              </a>
            </div>
          </div>
        </div>
      </section>

      {/* COMO FUNCIONA */}
      <section id="como-funciona" className="py-14 sm:py-20 bg-white dark:bg-[#151515] border-y border-[#E5E5E5] dark:border-[#2A2A2A] px-4 sm:px-6 transition-colors duration-200">
        <div className="max-w-7xl mx-auto">
          <div className="text-center max-w-2xl mx-auto mb-10 sm:mb-14">
            <span className="text-[10px] sm:text-xs font-bold uppercase tracking-[0.18em] text-[#737373] dark:text-[#A3A3A3]">Simples assim</span>
            <h2 className="text-2xl sm:text-3xl font-extrabold text-[#171717] dark:text-white tracking-[-0.04em] mt-2 mb-3">
              Marcou. Combinou. Confirmou.
            </h2>
            <p className="text-[#737373] dark:text-[#A3A3A3] text-xs sm:text-sm md:text-base">
              Encontre o espaço ideal e organize seu horário sem complicação.
            </p>
          </div>

          <div className="grid grid-cols-1 md:grid-cols-3 gap-5 sm:gap-6">
            {[
              { n: '01', t: 'Marque', d: 'Escolha a sala, a capacidade e o horário que fazem sentido para sua atividade.', dark: false },
              { n: '02', t: 'Combine', d: 'Faça login e confirme a combinação entre espaço, horário e participantes.', dark: true },
              { n: '03', t: 'Confirme', d: 'Sua reserva fica registrada e pronta para você usar no horário combinado.', dark: false },
            ].map((step) => (
              <div key={step.n} className="p-5 sm:p-6 rounded-2xl bg-[#FAFAF9] dark:bg-[#1C1C1C] border border-[#E5E5E5] dark:border-[#2A2A2A]">
                <div
                  className={`w-11 h-11 rounded-xl flex items-center justify-center font-extrabold text-base mb-5 ${
                    step.dark
                      ? 'bg-[#404040] dark:bg-[#D4D4D4] text-white dark:text-[#171717]'
                      : 'bg-[#171717] dark:bg-white text-white dark:text-[#171717]'
                  }`}
                >
                  {step.n}
                </div>
                <h3 className="font-extrabold text-[#171717] dark:text-white text-base sm:text-lg mb-2">{step.t}</h3>
                <p className="text-[#737373] dark:text-[#A3A3A3] text-xs sm:text-sm leading-relaxed">{step.d}</p>
              </div>
            ))}
          </div>
        </div>
      </section>

      {/* SALAS EM DESTAQUE */}
      <section id="destaques" className="py-14 sm:py-20 px-4 sm:px-6 max-w-7xl mx-auto">
        <div className="text-center max-w-2xl mx-auto mb-9 sm:mb-12">
          <span className="text-[10px] sm:text-xs font-bold uppercase tracking-[0.18em] text-[#737373] dark:text-[#A3A3A3]">Encontre seu espaço</span>
          <h2 className="text-2xl sm:text-3xl font-extrabold text-[#171717] dark:text-white tracking-[-0.04em] mt-2 mb-3">Salas em Destaque</h2>
          <p className="text-[#737373] dark:text-[#A3A3A3] text-xs sm:text-sm md:text-base">
            Uma seleção de espaços disponíveis. Faça login para consultar todas as opções.
          </p>
        </div>

        <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-5 sm:gap-6">
          {salasDestaque.map((sala) => (
            <div
              key={sala.id}
              className="bg-white dark:bg-[#191919] rounded-2xl border border-[#E5E5E5] dark:border-[#2A2A2A] shadow-sm hover:shadow-lg hover:-translate-y-0.5 transition duration-200 p-5 sm:p-6 flex flex-col justify-between"
            >
              <div>
                <div className="flex flex-wrap items-center justify-between gap-2 mb-4">
                  <span className="px-2.5 py-1 bg-[#F5F5F5] dark:bg-[#242424] text-[#404040] dark:text-[#D4D4D4] font-bold text-xs sm:text-sm rounded-lg border border-[#E5E5E5] dark:border-[#333333]">
                    {sala.id}
                  </span>
                  <span className="text-xs font-bold px-2.5 py-1 bg-[#FAFAF9] dark:bg-[#202020] text-[#737373] dark:text-[#A3A3A3] rounded-md border border-[#E5E5E5] dark:border-[#2A2A2A]">
                    {sala.capacidade} pessoas
                  </span>
                </div>

                <h3 className="font-extrabold text-[#171717] dark:text-white text-sm sm:text-base mb-2 leading-snug break-words">
                  {sala.localizacao}
                </h3>

                <p className="text-xs text-[#737373] dark:text-[#A3A3A3] mb-4">
                  <strong className="text-[#404040] dark:text-[#D4D4D4] font-bold">Responsável:</strong> {sala.responsavel}
                </p>
              </div>

              <a href="/login" className="mt-4 block w-full py-2.5 text-center bg-[#171717] dark:bg-[#2A2A2A] hover:bg-[#404040] dark:hover:bg-white dark:hover:text-[#171717] text-white font-bold text-xs rounded-xl transition duration-150">
                Combinar sala
              </a>
            </div>
          ))}

          {/* CTA */}
          <div className="bg-[#171717] dark:bg-white rounded-2xl p-5 sm:p-6 text-white dark:text-[#171717] flex flex-col justify-between shadow-md">
            <div>
              <span className="px-3 py-1 bg-white/10 dark:bg-[#F5F5F5] text-[#E5E5E5] dark:text-[#404040] font-bold text-xs rounded-lg inline-block mb-4">
                +35 salas disponíveis
              </span>
              <h3 className="font-extrabold text-lg sm:text-xl mb-2 tracking-tight">Ainda procurando?</h3>
              <p className="text-[#A3A3A3] dark:text-[#737373] text-xs leading-relaxed">
                Acesse o painel para consultar horários, verificar disponibilidade e gerenciar suas combinações.
              </p>
            </div>
            <a href="/login" className="mt-6 block w-full py-2.5 text-center bg-white dark:bg-[#171717] text-[#171717] dark:text-white hover:bg-[#E5E5E5] dark:hover:bg-[#404040] font-bold text-xs rounded-xl transition duration-150">
              Ver todas as salas
            </a>
          </div>
        </div>
      </section>

      {/* FOOTER */}
      <footer className="border-t border-[#E5E5E5] dark:border-[#2A2A2A] bg-white dark:bg-[#111111] py-7 sm:py-9 px-4 sm:px-6 transition-colors duration-200">
        <div className="max-w-7xl mx-auto flex flex-col sm:flex-row items-center justify-between gap-4">
          <CombinaLogo iconSize={28} textSize="text-base" />
          <p className="text-xs text-[#737373] dark:text-[#737373]">
            © {new Date().getFullYear()} Combinaí. Seu espaço. Seu horário.
          </p>
        </div>
      </footer>
    </div>
  );
}

export default Home;