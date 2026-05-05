import { useForm } from 'react-hook-form'
import { zodResolver } from '@hookform/resolvers/zod'
import { z } from 'zod'
import { useNavigate, Link } from 'react-router-dom'
import { register as registerUser } from '../api/auth'
import { useAuthStore } from '../store/authStore'
import { useState } from 'react'

const schema = z.object({
  username: z.string().min(3, 'At least 3 characters').max(50),
  email: z.string().email('Valid email required'),
  password: z.string().min(8, 'At least 8 characters'),
})
type FormData = z.infer<typeof schema>

export default function RegisterPage() {
  const navigate = useNavigate()
  const loginStore = useAuthStore((s) => s.login)
  const [serverError, setServerError] = useState<string | null>(null)

  const { register, handleSubmit, formState: { errors, isSubmitting } } = useForm<FormData>({
    resolver: zodResolver(schema),
  })

  const onSubmit = async (data: FormData) => {
    setServerError(null)
    try {
      const res = await registerUser(data.username, data.email, data.password)
      loginStore(res.data.token, res.data.username, res.data.role)
      navigate('/')
    } catch (err: unknown) {
      const msg = (err as { response?: { data?: { message?: string } } })
        ?.response?.data?.message
      setServerError(msg ?? 'Registration failed.')
    }
  }

  const field = (label: string, name: keyof FormData, type = 'text') => (
    <div>
      <label className="block text-sm text-stone-400 mb-1">{label}</label>
      <input
        type={type}
        {...register(name)}
        className="w-full bg-souls-stone border border-souls-gold/30 rounded px-3 py-2 text-stone-200 focus:outline-none focus:border-souls-gold"
      />
      {errors[name] && (
        <p className="text-red-400 text-xs mt-1">{errors[name]?.message}</p>
      )}
    </div>
  )

  return (
    <div className="max-w-md mx-auto mt-16">
      <h1 className="font-serif text-2xl text-souls-gold mb-8 text-center">Create Account</h1>

      <form onSubmit={handleSubmit(onSubmit)} className="space-y-4">
        {field('Username', 'username')}
        {field('Email', 'email', 'email')}
        {field('Password', 'password', 'password')}

        {serverError && <p className="text-red-400 text-sm">{serverError}</p>}

        <button
          type="submit"
          disabled={isSubmitting}
          className="w-full py-2 bg-souls-gold/20 border border-souls-gold text-souls-gold rounded hover:bg-souls-gold/30 transition-colors disabled:opacity-50"
        >
          {isSubmitting ? 'Creating account…' : 'Register'}
        </button>
      </form>

      <p className="text-center text-sm text-souls-ash mt-6">
        Already have an account?{' '}
        <Link to="/login" className="text-souls-gold hover:underline">
          Log in
        </Link>
      </p>
    </div>
  )
}
